DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM films;
ALTER SEQUENCE user_seq RESTART WITH 1;
ALTER SEQUENCE film_seq RESTART WITH 1;

INSERT INTO users (login, password) VALUES
  ('User1', '123'),
  ('User2', '123'),
  ('User3', '123'),
  ('User4', '123'),
  ('User5', '123'),
  ('Admin', '123');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3),
  ('ROLE_USER', 4),
  ('ROLE_USER', 5),
  ('ROLE_ADMIN', 6),
  ('ROLE_USER', 6);

INSERT INTO films (title, image, description, genre) VALUES
  ('Film 01', './resources/films_img/xmen01.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 02', './resources/films_img/xmen02.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 03', './resources/films_img/xmen03.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 04', './resources/films_img/xmen04.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 05', './resources/films_img/xmen05.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 06', './resources/films_img/xmen06.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 07', './resources/films_img/xmen07.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 08', './resources/films_img/xmen08.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 09', './resources/films_img/xmen09.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 10', './resources/films_img/xmen10.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION'),
  ('Film 11', './resources/films_img/xmen11.jpg', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.', 'ACTION');