DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM films;
ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (login) VALUES
  ('User1'),
  ('User2'),
  ('User3'),
  ('Admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3),
  ('ROLE_ADMIN', 4);

INSERT INTO films (title, image, description, genre) VALUES
  ('Film 01', './resources/films_img/xmen01.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 02', './resources/films_img/xmen02.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 03', './resources/films_img/xmen03.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION');