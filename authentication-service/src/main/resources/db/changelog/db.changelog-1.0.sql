--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS users
(
    id                SERIAL PRIMARY KEY,
    email             VARCHAR(256) UNIQUE NOT NULL,
    password          VARCHAR(256)        NOT NULL,
    email_is_verified BOOLEAN             NOT NULL DEFAULT false
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS roles
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(16) UNIQUE NOT NULL
);

--changeset re1kur:3
CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INTEGER,
    role_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

--changeset re1kur:4
INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN')
