#!/bin/bash

set -e

if [ -z "$GRAALVM_HOME" ]; then
  echo "GRAALVM_HOME is not set, exit."
  exit 1
fi

# Install `kms-sso-security` library to local Maven repository
# https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html
mvn initialize

# Build native image
mvn clean package

# Create output directory
mkdir -p build

install target/openvpn-auth-kingsoft-sso build/
install pkg/auth-kingsoft-sso.sh build/
install --mode=644 pkg/auth-kingsoft-sso.conf build/
