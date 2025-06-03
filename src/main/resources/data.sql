-- Sample Users
INSERT INTO survey_user (id, full_name, email, date_of_birth, contact_number)
VALUES (1, 'John Doe', 'john.doe@example.com', '1990-05-15', '034567890');

INSERT INTO survey_user (id, full_name, email, date_of_birth, contact_number)
VALUES (2, 'Jane Smith', 'jane.smith@example.com', '1985-10-22', '0987654321');

INSERT INTO survey_user (id, full_name, email, date_of_birth, contact_number)
VALUES (3, 'Alex Young', 'alex.y@example.com', '2010-03-10', '0122334455');

-- Sample Ratings
INSERT INTO ratings (id, movies_rating, radio_rating, eat_out_rating, tv_rating, user_id)
VALUES (1, 4, 3, 5, 2, 1);

INSERT INTO ratings (id, movies_rating, radio_rating, eat_out_rating, tv_rating, user_id)
VALUES (2, 5, 4, 4, 3, 2);

INSERT INTO ratings (id, movies_rating, radio_rating, eat_out_rating, tv_rating, user_id)
VALUES (3, 3, 2, 5, 4, 3);

-- Favorite Foods
INSERT INTO favorite_foods (response_id, food) VALUES (1, 'Pizza');
INSERT INTO favorite_foods (response_id, food) VALUES (1, 'Pasta');
INSERT INTO favorite_foods (response_id, food) VALUES (2, 'pap and wors');
INSERT INTO favorite_foods (response_id, food) VALUES (2, 'Others');
INSERT INTO favorite_foods (response_id, food) VALUES (3, 'Pizza');
INSERT INTO favorite_foods (response_id, food) VALUES (3, 'pap and wors');