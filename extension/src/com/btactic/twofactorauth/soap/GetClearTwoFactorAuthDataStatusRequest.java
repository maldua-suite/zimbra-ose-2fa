/*
 * ***** BEGIN LICENSE BLOCK *****
 *
 * Zimbra OSE 2FA Extension
 * Copyright (C) 2023 BTACTIC, S.C.C.L.
 *
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2015, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK *****
 */
package com.btactic.twofactorauth.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.zimbra.common.soap.AdminConstants;
import com.zimbra.soap.admin.type.CosSelector;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name=AdminConstants.E_GET_CLEAR_TWO_FACTOR_AUTH_DATA_STATUS_REQUEST)
public class GetClearTwoFactorAuthDataStatusRequest {
    @XmlElement(name=AdminConstants.E_COS, required=false)
    private CosSelector cos;

    private GetClearTwoFactorAuthDataStatusRequest() {}

    public GetClearTwoFactorAuthDataStatusRequest(CosSelector cos) {
        setCos(cos);
    }

    public void setCos(CosSelector cos) {this.cos = cos; }
    public CosSelector getCos() {return cos; }
}
