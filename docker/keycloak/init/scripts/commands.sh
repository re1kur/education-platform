#!/bin/sh
set -e
cd /opt/keycloak/bin

./kc.sh import --dir /tmp/realms --override true

exec ./kc.sh start-dev &

echo "Waiting for Keycloak to start..."
until { printf 'HEAD /health/ready HTTP/1.0\r\n\r\n' >&0; grep 'HTTP/1.0 200'; } 0<>/dev/tcp/localhost/9000; do
  sleep 5
done

echo "Keycloak is ready. Creating admin user..."

./kcadm.sh config credentials \
    --server http://localhost:8080 \
    --realm master \
    --user ${KC_BOOTSTRAP_ADMIN_USERNAME}\
    --password ${KC_BOOTSTRAP_ADMIN_PASSWORD}

./kcadm.sh create users -r master -s username=${KEYCLOAK_ADMIN_NAME} -s enabled=true

./kcadm.sh set-password -r master --username ${KEYCLOAK_ADMIN_NAME} --new-password ${KEYCLOAK_ADMIN_PASSWORD}

./kcadm.sh add-roles -r master --uusername ${KEYCLOAK_ADMIN_NAME} --rolename admin

echo "Permanent admin user created successfully"

USER_ID=$(./kcadm.sh get users -r master -q username="${KC_BOOTSTRAP_ADMIN_USERNAME}" --fields id | grep -o '"id"[[:space:]]*:[[:space:]]*"[^"]*"' | head -1 | cut -d'"' -f4)

echo "Deleting temporary admin user ${KC_BOOTSTRAP_ADMIN_USERNAME}... "

./kcadm.sh delete users/${USER_ID}

echo "Temporary admin user ${KC_BOOTSTRAP_ADMIN_USERNAME} is deleted... "

wait