#!/bin/bash

set -e

if [ -z "$GRAALVM_HOME" ]; then
  echo "GRAALVM_HOME is not set, exit."
  exit 1
fi

# Install `kms-sso-security` library to local Maven repository
# https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html
mvn initialize

# Build fat jar
mvn clean package

# Create output directory
mkdir -p build

# Generate Linux native executable from fat jar
"$GRAALVM_HOME/bin/native-image" \
  --no-fallback \
  --report-unsupported-elements-at-runtime \
  --enable-https \
  --static \
  -jar target/openvpn-auth-kingsoft-sso.jar \
  build/openvpn-auth-kingsoft-sso

chmod 755 build/openvpn-auth-kingsoft-sso

install pkg/auth-kingsoft-sso.sh build/
install --mode=644 pkg/auth-kingsoft-sso.conf build/
