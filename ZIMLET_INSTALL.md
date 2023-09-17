# Install Zimbra OSE 2FA QR Zimlet

## Warning

**These are developer instructions.**

For Admin installation instructions please check [README.md](README.md) instead.

## Requisites

You have succesfully built Zimbra OSE 2FA QR Zimlet using [ZIMLET_BUILD.md](ZIMLET_BUILD.md) instructions.

## Installation

Get `/tmp/zimbra-ose-2fa/zimlet/com_btactic_twofactorauth_qr.zip` from your build machine and copy it to your production machine on `/tmp/com_btactic_twofactorauth_qr.zip` .

This needs to be run as the root user:

```
chown zimbra:zimbra /tmp/com_btactic_twofactorauth_qr.zip
su - zimbra - c 'zmzimletctl deploy /tmp/com_btactic_twofactorauth_qr.zip'
```

## Zimbra Mailbox restart not needed

For the new Zimlet to be used the Zimbra Mailbox does not need to be restarted.

## Network Edition notes

This specific QR zimlet should work alongside the Zimbra NE installation and should show a QR in the classic view.
