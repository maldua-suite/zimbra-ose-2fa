# Install Zimbra OSE 2FA Admin Zimlet

## Warning

**These are developer instructions.**

For Admin installation instructions please check [README.md](README.md) instead.

## Requisites

You have succesfully built zimbra-ose-2fa admin zimlet using [ADMINZIMLET_BUILD.md](ADMINZIMLET_BUILD.md) instructions.

## Installation

**Note**: This procedure has to be done on all of your mailboxes.

Get `/tmp/zimbra-ose-2fa/adminZimlet/com_btactic_twofactorauth_admin.zip` from your build machine and copy it to your production machine on `/tmp/com_btactic_twofactorauth_admin.zip` .

This needs to be run as the root user:

```
chown zimbra:zimbra /tmp/com_btactic_twofactorauth_admin.zip
```

And then:
```
sudo su - zimbra
zmzimletctl deploy /tmp/com_btactic_twofactorauth_admin.zip
```
.

## Zimbra Mailbox restart

For the new admin Zimlet to be used the Zimbra Mailbox **does not need** to be restarted.
How it is recommended.

```
sudo su - zimbra -c 'zmmailboxdctl restart'
```

## Network Edition notes

This is not supposed to work in a Zimbra NE installation.
If you insist on using this admin zimlet in a Zimbra NE installation please move the original `/opt/zimbra/zimlets/com_zimbra_two_factor_auth.zip` file from Zimbra NE somewhere else so that they do not collide. 
