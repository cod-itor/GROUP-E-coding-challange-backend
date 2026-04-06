CREATE EXTENSION IF NOT EXISTS "pgcrypto";

Create database "hrd_community_db";

CREATE TABLE IF NOT EXISTS generations
(
    gen_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    gen_num INTEGER NOT NULL UNIQUE
);

INSERT INTO generations (gen_num)
VALUES (11),
       (12),
       (13),
       (14)
ON CONFLICT (gen_num) DO NOTHING;

CREATE TABLE IF NOT EXISTS classrooms
(
    class_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    class_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS topics
(
    topic_id    UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    topic_title VARCHAR(150) NOT NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS users
(
    user_id      UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    full_name    VARCHAR(30)  NOT NULL,
    email        VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    user_photo   TEXT,
    is_student   BOOLEAN      NOT NULL DEFAULT FALSE,
    gen_id       UUID REFERENCES generations (gen_id),
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS students
(
    stu_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id  UUID NOT NULL UNIQUE REFERENCES users (user_id) ON DELETE CASCADE,
    class_id UUID NOT NULL REFERENCES classrooms (class_id)
);

CREATE TABLE IF NOT EXISTS questions
(
    question_id    UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    question_title VARCHAR(255) NOT NULL,
    question_desc  TEXT,
    code_snippet   TEXT,
    topic_id       UUID         NOT NULL REFERENCES topics (topic_id),
    tags           TEXT[],
    created_by     UUID         NOT NULL REFERENCES users (user_id),
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS answers
(
    answer_id    UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    question_id  UUID      NOT NULL REFERENCES questions (question_id) ON DELETE CASCADE,
    created_by   UUID      NOT NULL REFERENCES users (user_id),
    answer_body  TEXT      NOT NULL,
    upvote_count INTEGER   NOT NULL DEFAULT 0,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS upvotes
(
    upvote_id  UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    user_id    UUID      NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    answer_id  UUID      NOT NULL REFERENCES answers (answer_id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, answer_id)
);

CREATE TABLE IF NOT EXISTS save_lists
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by  UUID NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES questions (question_id) ON DELETE CASCADE,
    UNIQUE (created_by, question_id)
);

CREATE TABLE IF NOT EXISTS verifications
(
    veri_id     UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    verified_by UUID      NOT NULL REFERENCES users (user_id),
    answer_id   UUID      NOT NULL UNIQUE REFERENCES answers (answer_id) ON DELETE CASCADE,
    score       SMALLINT  NOT NULL CHECK (score BETWEEN 1 AND 10),
    note        TEXT,
    verified_at TIMESTAMP NOT NULL DEFAULT NOW()
);

ALTER TABLE users
    ALTER COLUMN gen_id DROP NOT NULL;

ALTER TABLE users
    ALTER COLUMN is_student SET DEFAULT TRUE;

CREATE OR REPLACE FUNCTION set_default_generation()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.gen_id IS NULL THEN
        SELECT gen_id INTO NEW.gen_id FROM generations ORDER BY gen_num DESC LIMIT 1;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_latest_generation
    BEFORE INSERT
    ON users
    FOR EACH ROW
EXECUTE FUNCTION set_default_generation();

