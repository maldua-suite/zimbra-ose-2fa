# Zimbra OSE 2FA

**Warning: THIS PROJECT SHOULD BE CONSIDERED AS BETA QUALITY.**

This project aims to build an Open Source replacement of:
- Zimbra Network Edition 2FA Extension
- Zimbra Network Edition 2FA Administration zimlet.

For the final user UI it relies on current Zimbra OSE support for 2FA integrated on the Webmail.

## Admin documentation

### Quick installation instructions

**Requisites:**

- unzip

```
apt install unzip
```

**WARNING:** Please change **0.1.0** with whatever it's the latest released version.

```
sudo -i # Become root
cd /tmp
wget 'https://github.com/btactic/zimbra-ose-2fa/releases/download/v0.1.0/zimbra-ose-2fa_0.1.0.zip'
unzip zimbra-ose-2fa_0.1.0.zip
cd zimbra-ose-2fa_0.1.0
cp zetatwofactorauth.jar /opt/zimbra/lib/ext/twofactorauth/zetatwofactorauth.jar
chown zimbra:zimbra com_btactic_twofactorauth_admin.zip
su - zimbra -c 'zmzimletctl deploy ./com_btactic_twofactorauth_admin.zip'
```

## Developer documentation

This documentation is aimed at developers, not at admins.

### How to build the extension

- Check: [EXTENSION_BUILD.md](EXTENSION_BUILD.md) on how to build the Extension.

### How to install the extension

- Check: [EXTENSION_INSTALL.md](EXTENSION_INSTALL.md) on how to install the Extension.

### How to build the admin zimlet

- Check: [ADMINZIMLET_BUILD.md](ADMINZIMLET_BUILD.md) on how to build the Administration Console Zimlet.

### How to install the admin zimlet

- Check: [ADMINZIMLET_INSTALL.md](ADMINZIMLET_INSTALL.md) on how to install the Administration Console Zimlet.

### How to release the extension and admin zimlet

- Check: [RELEASE.md](RELEASE.md) on how to release the extension and admin zimlet.

## Licenses

### License (Extension)

```
Zimbra OSE 2FA Extension
Copyright (C) 2023 BTACTIC, S.C.C.L.

Zimbra Collaboration Suite Server
Copyright (C) 2007, 2008, 2009, 2010, 2013, 2014 Zimbra, Inc.

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software Foundation,
version 2 of the License.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with this program.
If not, see <http://www.gnu.org/licenses/>.
```

### License (Administration zimlet)

```
Zimbra OSE 2FA Administration zimlet
Copyright (C) 2023 BTACTIC, S.C.C.L.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see http://www.gnu.org/licenses/.
```
