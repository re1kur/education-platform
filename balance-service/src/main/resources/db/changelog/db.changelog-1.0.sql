--liquibase formatted sql

--changeset re1kur:1

CREATE TABLE IF NOT EXISTS balances
(
    user_id UUID PRIMARY KEY,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE
);