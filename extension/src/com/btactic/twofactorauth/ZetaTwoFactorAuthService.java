/*
 * Zimbra OSE 2FA Administration zimlet
 * Copyright (C) 2023 BTACTIC, S.C.C.L.
 */
package com.btactic.twofactorauth;

import com.zimbra.soap.DocumentDispatcher;
import com.zimbra.soap.DocumentService;
import com.zimbra.cs.service.account.AccountService;

import com.zimbra.common.soap.AccountConstants;

import com.btactic.twofactorauth.soap.EnableTwoFactorAuth;

public class ZetaTwoFactorAuthService extends AccountService implements DocumentService {

    public void registerHandlers(DocumentDispatcher dispatcher) {
        dispatcher.registerHandler(AccountConstants.ENABLE_TWO_FACTOR_AUTH_REQUEST, new EnableTwoFactorAuth());
    }

}
