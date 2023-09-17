#!/bin/bash

function set_zimlet_as_compulsory_at_all_cos () {

  for ncosid in $(su - zimbra -c 'zmprov getAllCos -v' | grep -E '^zimbraId: ' | sed -e 's/zimbraId: //g'); do
    cosname=$(su - zimbra -c 'zmprov getCos '"${ncosid}"' cn' | grep -E '^cn:' | sed -e 's/cn: //g')
    echo "Setting com_btactic_twofactorauth_qr as compulsory in: '""${cosname}""' CoS."
    su - zimbra -c 'zmprov modifyCos '"${ncosid}"' -zimbraZimletAvailableZimlets +com_btactic_twofactorauth_qr'
    su - zimbra -c 'zmprov modifyCos '"${ncosid}"' +zimbraZimletAvailableZimlets !com_btactic_twofactorauth_qr'
  done

}

if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

IS_COMPULSORY="NO"

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
