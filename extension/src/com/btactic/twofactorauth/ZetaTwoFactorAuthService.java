/*
 * Zimbra OSE 2FA Extension
 * Copyright (C) 2023 BTACTIC, S.C.C.L.
 */
package com.btactic.twofactorauth;

import com.zimbra.soap.DocumentDispatcher;
import com.zimbra.soap.DocumentService;
import com.zimbra.cs.service.account.AccountService;

import com.zimbra.common.soap.AccountConstants;

import com.btactic.twofactorauth.soap.EnableTwoFactorAuth;
import com.btactic.twofactorauth.soap.DisableTwoFactorAuth;
import com.btactic.twofactorauth.soap.CreateAppSpecificPassword;
import com.btactic.twofactorauth.soap.RevokeAppSpecificPassword;
import com.btactic.twofactorauth.soap.GetAppSpecificPasswords;
import com.btactic.twofactorauth.soap.GetScratchCodes;
import com.btactic.twofactorauth.soap.GenerateScratchCodes;
import com.btactic.twofactorauth.soap.GetTrustedDevices;
import com.btactic.twofactorauth.soap.RevokeTrustedDevice;
import com.btactic.twofactorauth.soap.RevokeOtherTrustedDevices;

public class ZetaTwoFactorAuthService extends AccountService implements DocumentService {

    public void registerHandlers(DocumentDispatcher dispatcher) {
        dispatcher.registerHandler(AccountConstants.ENABLE_TWO_FACTOR_AUTH_REQUEST, new EnableTwoFactorAuth());
        dispatcher.registerHandler(AccountConstants.DISABLE_TWO_FACTOR_AUTH_REQUEST, new DisableTwoFactorAuth());
        dispatcher.registerHandler(AccountConstants.CREATE_APP_SPECIFIC_PASSWORD_REQUEST, new CreateAppSpecificPassword());
        dispatcher.registerHandler(AccountConstants.REVOKE_APP_SPECIFIC_PASSWORD_REQUEST, new RevokeAppSpecificPassword());
        dispatcher.registerHandler(AccountConstants.GET_APP_SPECIFIC_PASSWORDS_REQUEST, new GetAppSpecificPasswords());
        dispatcher.registerHandler(AccountConstants.GET_SCRATCH_CODES_REQUEST, new GetScratchCodes());
        dispatcher.registerHandler(AccountConstants.GENERATE_SCRATCH_CODES_REQUEST, new GenerateScratchCodes());
        dispatcher.registerHandler(AccountConstants.GET_TRUSTED_DEVICES_REQUEST, new GetTrustedDevices());
        dispatcher.registerHandler(AccountConstants.REVOKE_TRUSTED_DEVICE_REQUEST, new RevokeTrustedDevice());
        dispatcher.registerHandler(AccountConstants.REVOKE_OTHER_TRUSTED_DEVICES_REQUEST, new RevokeOtherTrustedDevices());
    }

}
