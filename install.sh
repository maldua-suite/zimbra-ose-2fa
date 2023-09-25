#!/bin/bash

function usage () {

cat << EOF

$0
Regular installation.

$0 --help
Print this help.

EOF

}

function restart_notice () {

cat << EOF

- Zimbra 2FA Extension
- Zimbra 2FA Admin zimlet
- Zimbra 2FA QR addon
were installed.

Please restart mailboxd thanks to:

su - zimbra -c 'zmmailboxdctl restart'

so that this new extension is used.
EOF

}

if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit 1
fi

if [[ "$1" == '--help' ]]
then
    usage
    exit 0
fi

chown zimbra:zimbra $(pwd)
chown zimbra:zimbra com_btactic_twofactorauth_admin.zip

cp zetatwofactorauth.jar /opt/zimbra/lib/ext/twofactorauth/zetatwofactorauth.jar
su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_twofactorauth_admin.zip'

restart_notice
