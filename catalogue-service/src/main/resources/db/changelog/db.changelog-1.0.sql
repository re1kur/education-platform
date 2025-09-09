--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS categories
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(128) NOT NULL UNIQUE,
    preview_description VARCHAR(256),
    description TEXT,
    title_image_id UUID
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS products
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(128)   NOT NULL UNIQUE,
    price       SMALLINT NOT NULL CHECK ( price > 0),
    for_sale BOOLEAN NOT NULL DEFAULT FALSE,
    single BOOLEAN NOT NULL DEFAULT FALSE,
    description TEXT,
    category_id UUID       NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

--changeset re1kur:3
CREATE TABLE IF NOT EXISTS product_files
(
    product_id UUID,
    file_id UUID,
    FOREIGN KEY (product_id) REFERENCES products(id),
    PRIMARY KEY (product_id, file_id)
);

--changeset re1kur:4
CREATE TABLE IF NOT EXISTS catalogue_products
(
    product_id UUID PRIMARY KEY,
    priority SMALLINT NOT NULL DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

--changeset re1kur:5
CREATE TABLE IF NOT EXISTS orders
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    status VARCHAR(16) NOT NULL DEFAULT 'NEW' CHECK (status IN ('NEW', 'FAIL', 'SUCCESS')),
    transaction_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
);

--changeset re1kur:6
CREATE TABLE IF NOT EXISTS order_products
(
    order_id UUID,
    product_id UUID,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    PRIMARY KEY (order_id, product_id)
);

--changeset re1kur:7
CREATE TABLE IF NOT EXISTS user_products
(
    user_id UUID,
    product_id UUID,
    quantity SMALLINT NOT NULL DEFAULT 1,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    PRIMARY KEY(user_id, product_id)
);