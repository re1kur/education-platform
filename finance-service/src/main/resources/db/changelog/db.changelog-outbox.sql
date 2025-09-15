--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS outbox_events
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    payload TEXT NOT NULL,
    type VARCHAR(32) NOT NULL,
    reserved_to TIMESTAMP DEFAULT NULL
);