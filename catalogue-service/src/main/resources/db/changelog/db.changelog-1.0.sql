--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS categories
(
    id          SMALLSERIAL PRIMARY KEY,
    title       VARCHAR(128) NOT NULL UNIQUE,
    description TEXT,
    image_url   VARCHAR(255)
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS goods
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(128)   NOT NULL UNIQUE,
    category_id SMALLINT       NOT NULL,
    description TEXT,
    price       DECIMAL(19, 2) NOT NULL CHECK ( price > 0),
    in_stock    BOOLEAN DEFAULT TRUE,
    image_url   VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

--changeset re1kur:3
CREATE TABLE IF NOT EXISTS catalogue_goods
(
    goods_id INT PRIMARY KEY,
    "order" INT NOT NULL DEFAULT 0,
    FOREIGN KEY (goods_id) REFERENCES goods(id) ON DELETE CASCADE
);
