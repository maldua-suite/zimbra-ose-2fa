# Build Zimbra OSE 2FA QR Zimlet

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
cd /tmp/zimbra-ose-2fa/zimlet/com_btactic_twofactorauth_qr/
zip --quiet -r ../com_btactic_twofactorauth_qr.zip *
```

## Zip

A new zip file should be found at:
```
/tmp/zimbra-ose-2fa/zimlet/com_btactic_twofactorauth_qr.zip
```
.
