--liquibase formatted sql

--changeset re1kur:1

-- =========================
-- TASKS
-- =========================
INSERT INTO tasks (id, name, preview_description, description, cost)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Math Task 1', 'Simple addition', 'Solve 2 + 2', 10),
    ('22222222-2222-2222-2222-222222222222', 'History Task', 'WW2 facts', 'Answer who was the UK prime minister in 1940?', 15),
    ('33333333-3333-3333-3333-333333333333', 'Programming Task', 'Hello World', 'Write a program printing Hello World', 20);

-- =========================
-- TASK FILES
-- =========================
INSERT INTO task_files (task_id, file_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
    ('22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
    ('33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc'),
    ('33333333-3333-3333-3333-333333333333', 'dddddddd-dddd-dddd-dddd-dddddddddddd');

-- =========================
-- TASK ATTEMPTS
-- =========================
INSERT INTO task_attempts (id, task_id, user_id, comment, status)
VALUES
    ('44444444-4444-4444-4444-444444444444', '11111111-1111-1111-1111-111111111111', '99999999-9999-9999-9999-999999999999', 'My answer is 4', 'SUCCESS'),
    ('55555555-5555-5555-5555-555555555555', '22222222-2222-2222-2222-222222222222', '88888888-8888-8888-8888-888888888888', 'Churchill?', 'NEW'),
    ('66666666-6666-6666-6666-666666666666', '33333333-3333-3333-3333-333333333333', '77777777-7777-7777-7777-777777777777', 'Code compiles but not working', 'FAIL');

-- =========================
-- TASK ATTEMPT FILES
-- =========================
INSERT INTO task_attempt_files (task_attempt_id, file_id)
VALUES
    ('44444444-4444-4444-4444-444444444444', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
    ('55555555-5555-5555-5555-555555555555', 'ffffffff-ffff-ffff-ffff-ffffffffffff'),
    ('66666666-6666-6666-6666-666666666666', '12121212-1212-1212-1212-121212121212'),
    ('66666666-6666-6666-6666-666666666666', '13131313-1313-1313-1313-131313131313');

-- =========================
-- TASK ATTEMPT RESULTS
-- =========================
INSERT INTO task_attempt_results (task_attempt_id, checked_by, comment)
VALUES
    ('44444444-4444-4444-4444-444444444444', 'aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee', 'Correct, well done'),
    ('66666666-6666-6666-6666-666666666666', 'bbbbbbbb-cccc-dddd-eeee-ffffffffffff', 'Wrong output, please fix logic');
