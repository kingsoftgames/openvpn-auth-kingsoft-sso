#!/bin/bash

set -e

cd $(dirname "$0")

# https://openvpn.net/community-resources/reference-manual-for-openvpn-2-0/
# –auth-user-pass-verify via-file
readonly tmp_file="$1"
readonly username=$(head -n1 "$tmp_file")
readonly password=$(tail -n1 "$tmp_file")

source auth-kingsoft-sso.conf

export SSO_USERNAME="$username"
export SSO_PASSWORD="$password"

./openvpn-auth-kingsoft-sso
