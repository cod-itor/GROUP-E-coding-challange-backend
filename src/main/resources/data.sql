INSERT INTO topics (topic_title, is_active)
VALUES ('Java Programming', TRUE),
       ('Web Development with Spring Boot', TRUE),
       ('Relational Database Design (PostgreSQL)', TRUE),
       ('Frontend UI/UX with React.js', TRUE),
       ('Mobile Application Development (Kotlin)', TRUE),
       ('Microservices Architecture', TRUE),
       ('DevOps and CI/CD Pipelines', TRUE),
       ('Git & GitHub', TRUE),
       ('Documentation', TRUE);

INSERT INTO users (full_name, email, password, phone_number, is_student, gen_id)
VALUES ('Alice Johnson', 'alice.j@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345678', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 13)),
       ('Bob Smith', 'bob.s@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345679', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 11)),
       ('Charlie Day', 'charlie.d@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345680', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 11)),
       ('Diana Ross', 'diana.r@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345681', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 12)),
       ('Edward Teach', 'edward.t@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345682', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 11)),
       ('Fiona Apple', 'fiona.a@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345683', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 14)),
       ('George Lucas', 'george.l@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345684', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 14)),
       ('Hannah Montana', 'hannah.m@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345685', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 12)),
       ('Ian Fleming', 'ian.f@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345686', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 11)),
       ('Julia Child', 'julia.c@hrd.edu.kh', crypt('Teacher@123', gen_salt('bf')), '012345687', FALSE,
        (SELECT gen_id FROM generations WHERE gen_num = 13));

INSERT INTO classrooms (class_id, class_name) VALUES
                                                  (gen_random_uuid(), 'PVH'),
                                                  (gen_random_uuid(), 'SR'),
                                                  (gen_random_uuid(), 'PP');

INSERT INTO generations (gen_id, gen_num) VALUES (gen_random_uuid(), 1),
                                                 (gen_random_uuid(), 2),
                                                 (gen_random_uuid(), 3),
                                                 (gen_random_uuid(), 4),
                                                 (gen_random_uuid(), 5),
                                                 (gen_random_uuid(), 6),
                                                 (gen_random_uuid(), 7),
                                                 (gen_random_uuid(), 8),
                                                 (gen_random_uuid(), 9),
                                                 (gen_random_uuid(), 10);
