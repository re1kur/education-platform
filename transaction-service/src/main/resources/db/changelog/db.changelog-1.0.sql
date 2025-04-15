--liquibase formatted sql

--changeset re1kur:1

CREATE TABLE IF NOT EXISTS transaction_types
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS statuses
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL UNIQUE
);

--changeset re1kur:3
INSERT INTO statuses (name)
VALUES ('IN PROCESSING'),
       ('REJECTED'),
       ('APPROVED');


--changeset re1kur:4
INSERT INTO transaction_types (name)
VALUES ('DEBIT'),
       ('CREDIT');

--changeset re1kur:5

CREATE TABLE IF NOT EXISTS transactions
(
    id          UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    user_id     UUID                     NOT NULL,
    order_id    UUID                     NOT NULL,
    date        TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    type_id     SMALLINT                 NOT NULL,
    status_id   SMALLINT                 NOT NULL,
    amount      DECIMAL(19, 2)           NOT NULL,
    description VARCHAR(256),
    FOREIGN KEY (type_id) REFERENCES transaction_types (id),
    FOREIGN KEY (status_id) REFERENCES statuses (id)
);

--changeset re1kur:6
ALTER TABLE transactions
    ALTER COLUMN status_id SET DEFAULT 1;

--changeset re1kur:7

CREATE INDEX IF NOT EXISTS idx_transactions_user_id ON transactions (user_id);
CREATE INDEX IF NOT EXISTS idx_transactions_type_id ON transactions (type_id);
CREATE INDEX IF NOT EXISTS idx_transactions_type_id ON transactions (status_id);