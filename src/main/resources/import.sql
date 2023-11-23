INSERT INTO roles (id, name) VALUES(1, 'ADMIN'), (2, 'USER');

INSERT INTO users (email, first_name, last_name, registered_at, password) VALUES('gianlu@gmail.com', 'Gianlu', 'Giarde', '2023-11-20 17:00', '{noop}gianlu'), ('daje@gmail.com', 'Daje', 'Piombino', '2023-11-20 17:00','{noop}daje');

INSERT INTO users_roles (user_id, roles_id) VALUES(1, 1), (1, 2), (2, 2);