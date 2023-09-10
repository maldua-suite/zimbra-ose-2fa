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

    function com_btactic_twofactorauth_ext () {

    }

    if (window.console && console.log) {
        console.log("Start loading com_btactic_twofactorauth_admin.js");
    }

    // Show additional 2FA attributes for Accounts
    if (window.ZaAccount && ZaAccount.myXModel && ZaAccount.myXModel.items) {
        ZaAccount.myXModel.items.push({id: "zimbraFeatureTwoFactorAuthAvailable", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureTwoFactorAuthAvailable", choices: ZaModel.BOOLEAN_CHOICES});
        ZaAccount.myXModel.items.push({id: "zimbraFeatureTwoFactorAuthRequired", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureTwoFactorAuthRequired", choices: ZaModel.BOOLEAN_CHOICES});
        ZaAccount.myXModel.items.push({id: "zimbraFeatureAppSpecificPasswordsEnabled", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureAppSpecificPasswordsEnabled", choices: ZaModel.BOOLEAN_CHOICES});
        ZaAccount.myXModel.items.push({id: "zimbraTwoFactorAuthNumScratchCodes", type: _COS_NUMBER_, ref: "attrs/" + "zimbraTwoFactorAuthNumScratchCodes", minInclusive: 1, maxInclusive: 20});
    }

    if(ZaTabView.XFormModifiers["ZaAccountXFormView"]) {
        com_btactic_twofactorauth_ext.AccountXFormModifier= function (xFormObject,entry) {
            var cnt = xFormObject.items.length;
            var i = 0;
            for(i = 0; i <cnt; i++) {
                if(xFormObject.items[i].type=="switch")
                    break;
            }
            var tabBar = xFormObject.items[1] ;
            var twofactorauthTabIx = ++this.TAB_INDEX;
            tabBar.choices.push({value:twofactorauthTabIx, label:com_btactic_twofactorauth_admin.zimbraTwoFactorAuthTab});

            var twofactorauthAccountTab={
                type:_ZATABCASE_,
                numCols:1,
                caseKey:twofactorauthTabIx,
                items: [
                    {label: null, type: _OUTPUT_, value: com_btactic_twofactorauth_admin.zetaPromo, colSpan:"*", cssStyle:"font-size:20pt; font-weight: bold;"},
                    {type:_SPACER_, colSpan:"*"},
                    {type:_ZAGROUP_,
                        items:[
                            {ref: "zimbraFeatureTwoFactorAuthAvailable", type: _SUPER_CHECKBOX_, checkBoxLabel: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthAvailable, msgName: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthAvailable, trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel: ZaMsg.NAD_ResetToCOS},
                            {ref: "zimbraFeatureTwoFactorAuthRequired", type: _SUPER_CHECKBOX_, checkBoxLabel: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthRequired, msgName: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthRequired, trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel: ZaMsg.NAD_ResetToCOS},
                            {ref: "zimbraFeatureAppSpecificPasswordsEnabled", type: _SUPER_CHECKBOX_, checkBoxLabel: com_btactic_twofactorauth_admin.zimbraFeatureAppSpecificPasswordsEnabled, msgName: com_btactic_twofactorauth_admin.zimbraFeatureAppSpecificPasswordsEnabled, trueValue: "TRUE", falseValue: "FALSE", resetToSuperLabel: ZaMsg.NAD_ResetToCOS},
                            {ref: "zimbraTwoFactorAuthNumScratchCodes", type: _SUPER_TEXTFIELD_, txtBoxLabel: com_btactic_twofactorauth_admin.zimbraTwoFactorAuthNumScratchCodes, msgName: com_btactic_twofactorauth_admin.zimbraTwoFactorAuthNumScratchCodes, textFieldCssClass: "admin_xform_number_input", resetToSuperLabel: ZaMsg.NAD_ResetToCOS}
                        ]
                    }
                ]
            };

            xFormObject.items[i].items.push(twofactorauthAccountTab);
        }
        ZaTabView.XFormModifiers["ZaAccountXFormView"].push(com_btactic_twofactorauth_ext.AccountXFormModifier);
    }

    // Show additional 2FA attributes for Class of Service (CoS)
    if (window.ZaCos && ZaCos.myXModel && ZaCos.myXModel.items) {
        ZaCos.myXModel.items.push({id: "zimbraFeatureTwoFactorAuthAvailable", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureTwoFactorAuthAvailable", choices: ZaModel.BOOLEAN_CHOICES});
        ZaCos.myXModel.items.push({id: "zimbraFeatureTwoFactorAuthRequired", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureTwoFactorAuthRequired", choices: ZaModel.BOOLEAN_CHOICES});
        ZaCos.myXModel.items.push({id: "zimbraFeatureAppSpecificPasswordsEnabled", type: _COS_ENUM_, ref: "attrs/" + "zimbraFeatureAppSpecificPasswordsEnabled", choices: ZaModel.BOOLEAN_CHOICES});
        ZaCos.myXModel.items.push({id: "zimbraTwoFactorAuthNumScratchCodes", type: _COS_NUMBER_, ref: "attrs/" + "zimbraTwoFactorAuthNumScratchCodes", minInclusive: 1, maxInclusive: 20});
    }

    if(ZaTabView.XFormModifiers["ZaCosXFormView"]) {
        com_btactic_twofactorauth_ext.myCosXFormModifier= function (xFormObject,entry) {
            var cnt = xFormObject.items.length;
            var i = 0;
            for(i = 0; i <cnt; i++) {
                if(xFormObject.items[i].type=="switch")
                    break;
            }
            var tabBar = xFormObject.items[1] ;
            var twofactorauthTabIx = ++this.TAB_INDEX;
            tabBar.choices.push({value:twofactorauthTabIx, label:com_btactic_twofactorauth_admin.zimbraTwoFactorAuthTab});

            var twofactorauthAccountTab={
                type:_ZATABCASE_,
                numCols:1,
                caseKey:twofactorauthTabIx,
                items: [
                    {label: null, type: _OUTPUT_, value: com_btactic_twofactorauth_admin.zetaPromo, colSpan:"*", cssStyle:"font-size:20pt; font-weight: bold;"},
                    {type:_SPACER_, colSpan:"*"},
                    {type:_ZAGROUP_,
                        items:[
                            {ref: "zimbraFeatureTwoFactorAuthAvailable", type: _CHECKBOX_, label: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthAvailable, msgName: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthAvailable, trueValue: "TRUE", falseValue: "FALSE", labelLocation: _LEFT_},
                            {ref: "zimbraFeatureTwoFactorAuthRequired", type: _CHECKBOX_, label: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthRequired, msgName: com_btactic_twofactorauth_admin.zimbraFeatureTwoFactorAuthRequired, trueValue: "TRUE", falseValue: "FALSE", labelLocation: _LEFT_},
                            {ref: "zimbraFeatureAppSpecificPasswordsEnabled", type: _CHECKBOX_, label: com_btactic_twofactorauth_admin.zimbraFeatureAppSpecificPasswordsEnabled, msgName: com_btactic_twofactorauth_admin.zimbraFeatureAppSpecificPasswordsEnabled, trueValue: "TRUE", falseValue: "FALSE", labelLocation: _LEFT_},
                            {ref: "zimbraTwoFactorAuthNumScratchCodes", type: _TEXTFIELD_, label: com_btactic_twofactorauth_admin.zimbraTwoFactorAuthNumScratchCodes, msgName: com_btactic_twofactorauth_admin.zimbraTwoFactorAuthNumScratchCodes, textFieldCssClass: "admin_xform_number_input", labelLocation: _LEFT_}
                        ]
                    }
                ]
            };

            xFormObject.items[i].items.push(twofactorauthAccountTab);
        }
        ZaTabView.XFormModifiers["ZaCosXFormView"].push(com_btactic_twofactorauth_ext.myCosXFormModifier);
    }

}
