DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS ratings;
DROP TABLE IF EXISTS films;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS rating_seq;
DROP SEQUENCE IF EXISTS film_seq;

CREATE SEQUENCE user_seq START WITH 1;
CREATE SEQUENCE rating_seq START WITH 1;
CREATE SEQUENCE film_seq START WITH 1;

CREATE TABLE users
(
  id           INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
  login             VARCHAR                 NOT NULL,
  password         VARCHAR
);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE films
(
  id INTEGER PRIMARY KEY DEFAULT nextval('film_seq'),
  title             VARCHAR                 NOT NULL,
  image             VARCHAR                 NOT NULL,
  description       VARCHAR                 NOT NULL,
  genre             VARCHAR                 NOT NULL
);

CREATE TABLE ratings
(
  id      INTEGER PRIMARY KEY DEFAULT nextval('rating_seq'),
  user_id INTEGER NOT NULL,
  film_id INTEGER NOT NULL,
  rating INTEGER NOT NULL,
  FOREIGN KEY (film_id) REFERENCES films (id) ON DELETE CASCADE
)
