DROP TABLE IF EXISTS "user" CASCADE;
CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY  NOT NULL,
    name     CHARACTER VARYING(255) NOT NULL,
    surname  CHARACTER VARYING(255) NOT NULL,
    email    CHARACTER VARYING(255) NOT NULL,
    password CHARACTER VARYING(255) NOT NULL
);

DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    title  VARCHAR(255)          NOT NULL,
    author VARCHAR(255)          NOT NULL,
    genre  VARCHAR(255)          NOT NULL,
    user_id  BIGINT REFERENCES "user" (id)
);

INSERT INTO book (title, author, genre, user_id)
VALUES ('Anykščių Šilelis', 'Antanas Baranauskas', 'poem', null),
       ('Metai', 'Kristijonas Donelaitis', 'poem', null),
       ('Altorių šešėly', 'Vincas Mykolaitis-Putinas', 'psichological novel', null);


INSERT INTO "user" (name, surname, email, password)
VALUES ('admin', 'admin', 'admin', 'admin'),
       ('ohn', 'Doe', 'john.doe@mail.com', 'johny123')


DROP TABLE IF EXISTS role CASCADE;
CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE   NOT NULL
);

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    user_id BIGINT NOT NULL REFERENCES "user" (id),
    role_id BIGINT NOT NULL REFERENCES role (id)
);

INSERT INTO role (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (2, 1);
