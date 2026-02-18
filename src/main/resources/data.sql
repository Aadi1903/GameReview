-- Passwords are BCrypt encoded for 'user123' and 'admin123'
INSERT INTO users (username, password, role) VALUES ('user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7uqqCy3', 'USER');
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.7uqqCy3', 'MANAGER');

INSERT INTO games (title, genre, description, created_by) VALUES ('The Witcher 3', 'RPG', 'Open world fantasy RPG', 'admin');
INSERT INTO games (title, genre, description, created_by) VALUES ('Cyberpunk 2077', 'RPG', 'Futuristic open world RPG', 'admin');

INSERT INTO reviews (game_id, rating, comment, created_by) VALUES (1, 5, 'Masterpiece of storytelling.', 'user');
INSERT INTO reviews (game_id, rating, comment, created_by) VALUES (2, 4, 'Great atmosphere, much better now after patches.', 'user');
