--liquibase formatted sql

-- changeset re1kur:1
CREATE TABLE files
(
    id             UUID PRIMARY KEY,
    extension      VARCHAR(32)              NOT NULL,
    url            VARCHAR(2048)            NOT NULL UNIQUE,
    uploaded_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    url_expires_at TIMESTAMP WITH TIME ZONE
);