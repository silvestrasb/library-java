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


DROP TABLE IF EXISTS "user" CASCADE;

CREATE TABLE "user"
(
    id       BIGSERIAL PRIMARY KEY  NOT NULL,
    name     CHARACTER VARYING(255) NOT NULL,
    surname  CHARACTER VARYING(255) NOT NULL,
    email    CHARACTER VARYING(255) NOT NULL,
    password CHARACTER VARYING(255) NOT NULL
);

INSERT INTO "user" (name, surname, email, password)
VALUES ('ohn', 'Doe', 'john.doe@mail.com', 'johny123'),
     ('Mary', 'Frank', 'marry.frank@mail.com', 'mary123'),
     ('Vanessa', 'Bank', 'vanessa.bank@mail.com', 'vanessa123')
