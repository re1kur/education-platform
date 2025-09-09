--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS tasks
(
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name                VARCHAR(128)   NOT NULL,
    preview_description VARCHAR(256)   NOT NULL,
    description         TEXT           NOT NULL,
    cost                SMALLINT NOT NULL
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS task_files
(
    task_id         UUID,
    file_id         UUID,
    PRIMARY KEY (task_id, file_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);


--changeset re1kur:3
CREATE TABLE IF NOT EXISTS task_attempts
(
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id             UUID NOT NULL,
    user_id             UUID NOT NULL,
    comment             TEXT,
    status              VARCHAR(16) NOT NULL DEFAULT 'NEW' CHECK (status IN ('NEW', 'SUCCESS', 'FAIL')),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);

--changeset re1kur:4
CREATE TABLE IF NOT EXISTS task_attempt_files
(
    task_attempt_id     UUID,
    file_id             UUID,
    PRIMARY KEY (task_attempt_id, file_id),
    FOREIGN KEY (task_attempt_id) REFERENCES task_attempts(id)
);

--changeset re1kur:5
CREATE TABLE IF NOT EXISTS task_attempt_results
(
    task_attempt_id    UUID PRIMARY KEY,
    checked_by         UUID NOT NULL,
    checked_at         TIMESTAMP NOT NULL DEFAULT now(),
    comment            TEXT,
    FOREIGN KEY (task_attempt_id) REFERENCES task_attempts(id),
    UNIQUE (task_attempt_id, checked_by)
);

--changeset re1kur:6
CREATE TABLE IF NOT EXISTS user_completed_tasks
(
    user_id UUID NOT NULL,
    task_id UUID NOT NULL,
    FOREIGN KEY(task_id) REFERENCES tasks(id),
    PRIMARY KEY(user_id, task_id)
);