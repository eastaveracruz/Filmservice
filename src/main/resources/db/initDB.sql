DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 1;

CREATE TABLE users
(
  id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
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
  id SERIAL PRIMARY KEY ,
  title             VARCHAR                 NOT NULL,
  image             VARCHAR                 NOT NULL,
  description             VARCHAR                 NOT NULL,
  genre             VARCHAR                 NOT NULL
);