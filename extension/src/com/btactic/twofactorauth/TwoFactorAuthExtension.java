package com.btactic.twofactorauth;

import com.zimbra.cs.extension.ZimbraExtension;
import com.zimbra.soap.SoapServlet;
import com.zimbra.cs.account.ldap.ChangePasswordListener;
import com.zimbra.cs.account.ldap.ChangePasswordListener.InternalChangePasswordListenerId;
import com.btactic.twofactorauth.TwoFactorManager.TwoFactorPasswordChange;
import com.zimbra.cs.account.auth.twofactor.TwoFactorAuth;

/**
 * This extension registers a custom HTTP handler with <code>ExtensionDispatcherServlet<code>
 *
 * @author vmahajan
 */
public class TwoFactorAuthExtension implements ZimbraExtension {

    /**
     * Defines a name for the extension. It must be an identifier.
     *
     * @return extension name
     */
    public String getName() {
        return "twofactorauth";
    }

    /**
     * Initializes the extension. Called when the extension is loaded.
     *
     */
    public void init() {
        SoapServlet.addService("SoapServlet", new ZetaTwoFactorAuthService());
        SoapServlet.addService("AdminServlet", new ZetaTwoFactorAuthAdminService());

        InternalChangePasswordListenerId cplId = InternalChangePasswordListenerId.CPL_REVOKE_APP_PASSWORDS;
        ChangePasswordListener.registerInternal(cplId, new TwoFactorPasswordChange());

        TwoFactorAuth.setFactory("com.btactic.twofactorauth.TwoFactorManager$AuthFactory");
    }

    /**
     * Terminates the extension. Called when the server is shut down.
     */
    public void destroy() {
    }
}
