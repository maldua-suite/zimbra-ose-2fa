/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra OSE 2FA QR Zimlet
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

if(window.console && window.console.log) {
    window.console.log("Starting loading com_btactic_twofactorauth_qr.js")
}

if(ZaSettings && ZaSettings.EnabledZimlet["com_btactic_twofactorauth_qr"]){

    // STEP 1. Force Preferences load so that we can override TwoFactor_all.js functions
    if (appCtxt.get(ZmSetting.MAIL_PREFERENCES_ENABLED)) {
        AjxDispatcher.require(["PreferencesCore", "Preferences"]);
    }

    // STEP 3. Update QR when Next button is clicked.
    function twofactorauth_qr_setup() {

        // Create QR Div if it is not there yet.
        if (document.querySelectorAll('#twoFactorAuthQrDiv').length == 0) {
            var emptyDivForTwoFactorAuth = document.createElement("div");
            emptyDivForTwoFactorAuth.id = 'twoFactorAuthQrDiv';
            document.querySelectorAll('.email-key')[0].after(emptyDivForTwoFactorAuth);
        }

        // Move the key to the left so that the QR can be shown
        document.querySelectorAll('.email-key')[0].style.float="left";

        twofactorauth_next_button = $("[id$=" + '_button' + String(ZmTwoFactorSetupDialog.NEXT_BUTTON) + '_title' + "]")[0]
        twofactorauth_next_button.addEventListener('click', () => {
            $('#twoFactorAuthQrDiv')[0].innerHTML='';
            var twofactorauth_qr_email = appCtxt.get(ZmSetting.USERNAME); // username@example.net
            var twofactorauth_qr_secret = document.querySelectorAll('.email-key')[0].textContent;
            var twofactorauth_qr_issuer = window.location.host; // mail.example.net
            var qrcode = new QRCode($('#twoFactorAuthQrDiv')[0], {
                text: "otpauth://totp/" + twofactorauth_qr_email + "?secret=" + twofactorauth_qr_secret + "&issuer=" + twofactorauth_qr_issuer  ,
                width: 128,
                height: 128,
                colorDark : "#000000",
                colorLight : "#ffffff",
                correctLevel : QRCode.CorrectLevel.H
            });

        }
        );

    }

    // STEP 2. Default 2FA listener override with our Setup function
    ZmTwoFactorSetupDialog.prototype._beginSetupButtonListener = (function(_super) {
        return function() {
            twofactorauth_qr_setup();
            arguments[0] = arguments[0] - 1;
            return _super.apply(this, arguments);
        };

    })(ZmTwoFactorSetupDialog.prototype._beginSetupButtonListener);

}
