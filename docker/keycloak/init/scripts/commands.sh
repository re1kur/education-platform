#!/bin/sh
set -e
cd /opt/keycloak/bin

./kc.sh import --dir /tmp/realms --override true

exec ./kc.sh start-dev