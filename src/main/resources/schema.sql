CREATE EXTENSION IF NOT EXISTS "pgcrypto";

Create database "hrd_community_db";

CREATE TABLE generations
(
    gen_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    gen_num INTEGER NOT NULL UNIQUE
);

CREATE TABLE classrooms
(
    class_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    class_name VARCHAR(100) NOT NULL
);

CREATE TABLE topics
(
    topic_id    UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    topic_title VARCHAR(150) NOT NULL,
    is_active   BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);


CREATE TABLE users
(
    user_id      UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    full_name    VARCHAR(30)  NOT NULL,
    email        VARCHAR(50)  NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    user_photo   TEXT,
    is_student   BOOLEAN      NOT NULL DEFAULT FALSE,
    gen_id       UUID         NOT NULL REFERENCES generations (gen_id),
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE students
(
    stu_id   UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id  UUID NOT NULL UNIQUE REFERENCES users (user_id) ON DELETE CASCADE,
    class_id UUID NOT NULL REFERENCES classrooms (class_id)
);



CREATE TABLE questions
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

CREATE TABLE answers
(
    answer_id    UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    question_id  UUID      NOT NULL REFERENCES questions (question_id) ON DELETE CASCADE,
    created_by   UUID      NOT NULL REFERENCES users (user_id),
    answer_body  TEXT      NOT NULL,
    upvote_count INTEGER   NOT NULL DEFAULT 0,
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE upvotes
(
    upvote_id  UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    user_id    UUID      NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    answer_id  UUID      NOT NULL REFERENCES answers (answer_id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    UNIQUE (user_id, answer_id)
);

-- For Save Favourite Question
CREATE TABLE save_lists
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_by  UUID NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    question_id UUID NOT NULL REFERENCES questions (question_id) ON DELETE CASCADE,
    UNIQUE (created_by, question_id)
);

CREATE TABLE verifications
(
    veri_id     UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    verified_by UUID      NOT NULL REFERENCES users (user_id),
    answer_id   UUID      NOT NULL UNIQUE REFERENCES answers (answer_id) ON DELETE CASCADE,
    score       SMALLINT  NOT NULL CHECK (score BETWEEN 1 AND 10),
    note        TEXT,
    verified_at TIMESTAMP NOT NULL DEFAULT NOW()
);