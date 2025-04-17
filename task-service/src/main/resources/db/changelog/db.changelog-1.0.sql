--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS tracks
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE

);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS tasks
(
    id          SERIAL PRIMARY KEY,
    track_id    SMALLINT       NOT NULL,
    name        VARCHAR(128)   NOT NULL,
    description TEXT           NOT NULL,
    level       SMALLINT       NOT NULL CHECK (level BETWEEN 1 AND 3),
    cost        DECIMAL(19, 2) NOT NULL,
    FOREIGN KEY (track_id) REFERENCES tracks (id) ON DELETE CASCADE,
    CHECK (level = 1 AND cost BETWEEN 10 AND 25 OR
           level = 2 AND cost BETWEEN 26 AND 50 OR
           level = 3 AND cost BETWEEN 51 AND 100)
);

--changeset re1kur:3
CREATE TABLE IF NOT EXISTS users_tasks
(
    user_id UUID,
    task_id INTEGER,
    PRIMARY KEY (user_id, task_id),
    FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE
);

--changeset re1kur:4
CREATE TABLE IF NOT EXISTS users_tracks
(
    user_id  UUID,
    track_id SMALLINT,
    PRIMARY KEY (user_id, track_id),
    FOREIGN KEY (track_id) REFERENCES tracks (id) ON DELETE CASCADE
);
