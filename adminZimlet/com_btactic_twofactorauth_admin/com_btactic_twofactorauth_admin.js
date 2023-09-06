/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra OSE 2FA Extension
 * Copyright (C) 2023 BTACTIC, S.C.C.L.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * ***** END LICENSE BLOCK *****
 */

if(ZaSettings && ZaSettings.EnabledZimlet["com_btactic_twofactorauth_admin"]){

    if (window.console && console.log) {
        console.log("Start loading com_btactic_twofactorauth_admin.js");
    }

    // Show additional 2FA attributes for Accounts
    if (window.ZaAccount && ZaAccount.myXModel && ZaAccount.myXModel.items) {
        ZaAccount.myXModel.items.push({id: "zimbraFeatureTwoFactorAuthAvailable", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureTwoFactorAuthAvailable", choices: ZaModel.BOOLEAN_CHOICES});
    }

}
