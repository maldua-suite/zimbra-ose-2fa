#!/bin/bash

VERSION="$(head -n 1 VERSION)"
ZIP_DIR="zimbra-ose-2fa_${VERSION}"

# Build extension
cd extension
ant jar
cd ..

# Build admin zimlet
cd adminZimlet/com_btactic_twofactorauth_admin
sed -i 's/@@VERSION@@/'"${VERSION}"'/g' com_btactic_twofactorauth_admin.xml
zip --quiet -r ../com_btactic_twofactorauth_admin.zip *
cd ../..

# Zip directory
mkdir release/${ZIP_DIR}
cp extension/zetatwofactorauth.jar release/${ZIP_DIR}/zetatwofactorauth.jar
cp adminZimlet/com_btactic_twofactorauth_admin.zip release/${ZIP_DIR}/com_btactic_twofactorauth_admin.zip

# Zip file
cd release
zip --quiet -r ${ZIP_DIR}.zip ${ZIP_DIR}
cd ..
