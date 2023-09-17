#!/bin/bash

function set_zimlet_as_compulsory_at_all_cos () {

  for ncosid in $(su - zimbra -c 'zmprov getAllCos -v' | grep -E '^zimbraId: ' | sed -e 's/zimbraId: //g'); do
    cosname=$(su - zimbra -c 'zmprov getCos '"${ncosid}"' cn' | grep -E '^cn:' | sed -e 's/cn: //g')
    echo "Setting com_btactic_twofactorauth_qr as compulsory in: '""${cosname}""' CoS."
    su - zimbra -c 'zmprov modifyCos '"${ncosid}"' -zimbraZimletAvailableZimlets +com_btactic_twofactorauth_qr'
    su - zimbra -c 'zmprov modifyCos '"${ncosid}"' +zimbraZimletAvailableZimlets !com_btactic_twofactorauth_qr'
  done

}

function usage () {

cat << EOF

$0
Regular installation.

$0 --compulsory
Regular installation and make the 2FA QR zimlet compulsory in all of the CoS.

$0 --help
Print this help.

EOF

}

function restart_notice () {

cat << EOF

- Zimbra 2FA Extension
- Zimbra 2FA Admin zimlet
- Zimbra 2FA QR zimlet
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

IS_COMPULSORY="NO"

if [[ "$1" == '--help' ]]
then
    usage
    exit 0
fi

if [[ "$1" == '--compulsory' ]]
then
    IS_COMPULSORY="YES"
fi

chown zimbra:zimbra $(pwd)
chown zimbra:zimbra com_btactic_twofactorauth_admin.zip
chown zimbra:zimbra com_btactic_twofactorauth_qr.zip

cp zetatwofactorauth.jar /opt/zimbra/lib/ext/twofactorauth/zetatwofactorauth.jar
su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_twofactorauth_admin.zip'
su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_twofactorauth_qr.zip'

if [[ "${IS_COMPULSORY}" == 'YES' ]]
then
    set_zimlet_as_compulsory_at_all_cos
fi

restart_notice
