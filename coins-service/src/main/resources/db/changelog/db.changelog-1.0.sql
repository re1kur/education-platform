--liquibase formatted sql

--changeset re1kur:1

CREATE TABLE IF NOT EXISTS balances
(
    user_id UUID PRIMARY KEY,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00
);

--changeset re1kur:2

CREATE TABLE IF NOT EXISTS transaction_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

--changeset re1kur:3

CREATE TABLE IF NOT EXISTS transactions
(
    id          UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    user_id     UUID                     NOT NULL,
    date        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    type_id     INT                      NOT NULL,
    amount      DECIMAL(19, 2)           NOT NULL,
    description VARCHAR(256),
    FOREIGN KEY (type_id) REFERENCES transaction_types (id)
);

--changeset re1kur:4

CREATE INDEX idx_transactions_user_id ON transactions (user_id);
CREATE INDEX idx_transactions_type_id ON transactions (type_id);