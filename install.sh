#!/bin/bash

if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

chown zimbra:zimbra $(pwd)
chown zimbra:zimbra com_btactic_twofactorauth_admin.zip
chown zimbra:zimbra com_btactic_twofactorauth_qr.zip

cp zetatwofactorauth.jar /opt/zimbra/lib/ext/twofactorauth/zetatwofactorauth.jar
su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_twofactorauth_admin.zip'
su - zimbra -c 'zmzimletctl -l deploy '"$(pwd)"'/com_btactic_twofactorauth_qr.zip'
