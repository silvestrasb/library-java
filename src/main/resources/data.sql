DROP TABLE IF EXISTS book CASCADE;
CREATE TABLE book
(
    id     BIGSERIAL PRIMARY KEY NOT NULL,
    title  VARCHAR(255)          NOT NULL,
    author VARCHAR(255)          NOT NULL,
    genre  VARCHAR(255)          NOT NULL
);

INSERT INTO book (title, author, genre)
VALUES ('Anykščių Šilelis', 'Antanas Baranauskas', 'poem'),
       ('Metai', 'Kristijonas Donelaitis', 'poem'),
       ('Altorių šešėly', 'Vincas Mykolaitis-Putinas', 'psichological novel');


/*DROP TABLE IF EXISTS reader_user;

-- reader user
CREATE TABLE reader_user
(
    id serial NOT NULL,
    name character varying(45),
    surname character varying(45),
    email character varying(60),
    password character varying(45),
    PRIMARY KEY (id)
);

ALTER TABLE reader_user
    OWNER to adminuser;

-- movie
CREATE TABLE book
(
    id integer NOT NULL DEFAULT nextval('book_id_seq'::regclass),
    title character varying(45) COLLATE pg_catalog."default",
    author_name character varying(45) COLLATE pg_catalog."default",
    author_surname character varying(45) COLLATE pg_catalog."default",
    genre character varying(45) COLLATE pg_catalog."default",
    user_id integer,
    CONSTRAINT book_pkey PRIMARY KEY (id),
    CONSTRAINT "FK_USER_ID" FOREIGN KEY (user_id)
        REFERENCES public.reader_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

ALTER TABLE book
    OWNER TO adminuser;

-- test data
INSERT INTO reader_user (first_name, last_name, email, password)
VALUES ('Saulius',
        'Nauseda',
        'nauseda@lithuania.lt',
        'LTUETAXOROXO');

INSERT INTO book (title, author_name, author_surname, genre, user_id)
VALUES ("Horror of sleeping", 'Thomas', 'Sigolas', "horror", null);

*/