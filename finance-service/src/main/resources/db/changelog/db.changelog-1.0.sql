--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS accounts
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    balance SMALLINT NOT NULL DEFAULT 0,
    blocked BOOLEAN NOT NULL DEFAULT FALSE
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS transactions
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    account_id UUID NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    executed_at  TIMESTAMP NOT NULL,
    type     VARCHAR(16) NOT NULL CHECK (type IN ('DEBIT', 'CREDIT')),
    status   VARCHAR(16) NOT NULL DEFAULT 'NEW' CHECK (status IN ('NEW', 'FAIL', 'SUCCESS')),
    amount   SMALLINT NOT NULL CHECK (amount > 0),
    reference_type VARCHAR(16) NOT NULL CHECK(reference_type IN ('ORDER', 'TASK')),
    reference_id UUID NOT NULL,
    FOREIGN KEY(account_id) REFERENCES accounts(id)
);