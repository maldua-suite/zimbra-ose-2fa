/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra OSE 2FA Extension
 * Copyright (C) 2023 BTACTIC, S.C.C.L.
 *
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2008, 2009, 2010, 2013, 2014 Zimbra, Inc.
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.btactic.twofactorauth;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.zimbra.common.auth.twofactor.AuthenticatorConfig;
import com.zimbra.common.auth.twofactor.TwoFactorOptions.CodeLength;
import com.zimbra.common.auth.twofactor.TwoFactorOptions.HashAlgorithm;
import com.zimbra.cs.account.auth.twofactor.TwoFactorAuth;
import com.zimbra.cs.account.auth.twofactor.TwoFactorAuth.CredentialConfig;
import com.zimbra.cs.account.auth.twofactor.TrustedDevices;
import com.zimbra.common.auth.twofactor.TwoFactorOptions.Encoding;
import com.zimbra.common.auth.twofactor.TOTPAuthenticator;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.util.ZimbraLog;
import com.zimbra.cs.account.Account;
import com.zimbra.cs.account.AccountServiceException;
import com.zimbra.cs.account.AccountServiceException.AuthFailedServiceException;
import com.btactic.twofactorauth.AppSpecificPassword;
import com.btactic.twofactorauth.AppSpecificPassword.PasswordData;
import com.zimbra.cs.account.Config;
import com.zimbra.cs.account.DataSource;
import com.zimbra.cs.account.Provisioning;
import com.btactic.twofactorauth.TrustedDevice;
import com.btactic.twofactorauth.TrustedDeviceToken;
import com.zimbra.cs.account.ldap.ChangePasswordListener;
import com.zimbra.cs.account.ldap.LdapLockoutPolicy;
import com.zimbra.cs.ldap.LdapDateUtil;
import com.btactic.twofactorauth.CredentialGenerator;
import com.zimbra.common.soap.Element;

/**
 * This class is the main entry point for two-factor authentication.
 *
 * @author iraykin
 *
 */
public class ZetaTrustedDevices implements TrustedDevices {
    private Account account;
    private String acctNamePassedIn;
    private String secret;
    private List<String> scratchCodes;
    private Encoding encoding;
    private Encoding scratchEncoding;
    boolean hasStoredSecret;
    boolean hasStoredScratchCodes;
    private Map<String, AppSpecificPassword> appPasswords = new HashMap<String, AppSpecificPassword>();

    public ZetaTrustedDevices(Account account) throws ServiceException {
        this(account, account.getName());
    }

    public ZetaTrustedDevices(Account account, String acctNamePassedIn) throws ServiceException {
        this.account = account;
        this.acctNamePassedIn = acctNamePassedIn;
        disableTwoFactorAuthIfNecessary();
    }

    private void disableTwoFactorAuthIfNecessary() throws ServiceException {
        String encryptedSecret = account.getTwoFactorAuthSecret();
        if (!Strings.isNullOrEmpty(encryptedSecret)) {
            String decrypted = decrypt(account, encryptedSecret);
            String[] parts = decrypted.split("\\|");
            Date timestamp;
            if (parts.length == 1) {
                // For backwards compatability with the server version
                // that did not store a timestamp.
                timestamp = null;
            } else if (parts.length > 2) {
                throw ServiceException.FAILURE("invalid shared secret format", null);
            }
            try {
                timestamp = LdapDateUtil.parseGeneralizedTime(parts[1]);
            } catch (NumberFormatException e) {
                throw ServiceException.FAILURE("invalid shared secret timestamp", null);
            }
            Date lastDisabledDate = account.getCOS().getTwoFactorAuthLastReset();
            if (lastDisabledDate == null) {
                return;
            }
            if (timestamp == null || lastDisabledDate.after(timestamp)) {
                clearData();
            }
        }
    }

    public void clearData() throws ServiceException {
        account.setTwoFactorAuthEnabled(false);
        deleteCredentials();
        revokeAllAppSpecificPasswords();
        revokeAllTrustedDevices();
    }

    private static String decrypt(Account account, String encrypted) throws ServiceException {
        return DataSource.decryptData(account.getId(), encrypted);
    }

    private void deleteCredentials() throws ServiceException {
        account.setTwoFactorAuthSecret(null);
        account.setTwoFactorAuthScratchCodes(null);
    }

    public void revokeAllAppSpecificPasswords() throws ServiceException {
        for (String name: appPasswords.keySet()) {
            revokeAppSpecificPassword(name);
        }
    }

    @Override
    public TrustedDeviceToken registerTrustedDevice(Map<String, Object> deviceAttrs) throws ServiceException {
        if (!account.isFeatureTrustedDevicesEnabled()) {
            ZimbraLog.account.warn("attempting to register a trusted device when this feature is not enabled");
            return null;
        }
        TrustedDevice td = new TrustedDevice(account, deviceAttrs);
        ZimbraLog.account.debug("registering new trusted device");
        td.register();
        return td.getToken();
    }

    @Override
    public List<TrustedDevice> getTrustedDevices() throws ServiceException {
        List<TrustedDevice> trustedDevices = new ArrayList<TrustedDevice>();
        for (String encoded: account.getTwoFactorAuthTrustedDevices()) {
            try {
                TrustedDevice td = new TrustedDevice(account, encoded);
                if (td.isExpired()) {
                    td.revoke();
                }
                trustedDevices.add(td);
            } catch (ServiceException e) {
                ZimbraLog.account.error(e.getMessage());
                account.removeTwoFactorAuthTrustedDevices(encoded);
            }
        }
        return trustedDevices;
    }

    @Override
    public void revokeTrustedDevice(TrustedDeviceToken token) throws ServiceException {
        ZimbraLog.account.debug("revoking current trusted device");
        TrustedDevice td;
        try {
            td = TrustedDevice.byTrustedToken(account, token);
        } catch (AccountServiceException e) {
            ZimbraLog.account.warn("trying to revoke a trusted auth token with no corresponding device");
            return;
        }
        td.revoke();
    }

    @Override
    public void revokeAllTrustedDevices() throws ServiceException {
        ZimbraLog.account.debug("revoking all trusted devices");
        for (TrustedDevice td: getTrustedDevices()) {
            td.revoke();
        }
    }

    @Override
    public void revokeOtherTrustedDevices(TrustedDeviceToken token) throws ServiceException {
        if (token == null) {
            revokeAllTrustedDevices();
        } else {
            ZimbraLog.account.debug("revoking other trusted devices");
            for (TrustedDevice td: getTrustedDevices()) {
                if (!td.getTokenId().equals(token.getId())) {
                    td.revoke();
                }
            }
        }
    }

    @Override
    public void verifyTrustedDevice(TrustedDeviceToken token, Map<String, Object> attrs) throws ServiceException {
        ZimbraLog.account.debug("verifying trusted device");
        TrustedDevice td = TrustedDevice.byTrustedToken(account, token);
        if (td == null || !td.verify(attrs)) {
            throw AuthFailedServiceException.TWO_FACTOR_AUTH_FAILED(account.getName(), acctNamePassedIn, "trusted device cannot be verified");
        }
    }

    @Override
    public TrustedDeviceToken getTokenFromRequest(Element request, Map<String, Object> context) throws ServiceException {
        return null;
    }

    @Override
    public TrustedDevice getTrustedDeviceByTrustedToken(TrustedDeviceToken token) throws ServiceException {
        return null;
    }

}
