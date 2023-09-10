# Build Zimbra OSE 2FA Admin Zimlet

## Introduction

This is quite straight-forward to do.

## Requisites

- zip
- git

```
apt update
apt install zip git
```

## Prepare build environment

```
cd /tmp
git clone 'https://github.com/btactic/zimbra-ose-2fa.git'
```

## Build

```
cd /tmp/zimbra-ose-2fa/adminZimlet/com_btactic_twofactorauth_admin/
#
# Note change 0.1.0 with whatever is in VERSION file.
#
sed -i 's/@@VERSION@@/0.1.0/g' com_btactic_twofactorauth_admin.xml
zip --quiet -r ../com_btactic_twofactorauth_admin.zip *
```

## Zip

A new zip file should be found at:
```
/tmp/zimbra-ose-2fa/adminZimlet/com_btactic_twofactorauth_admin.zip
```
.
