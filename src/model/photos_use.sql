CREATE TABLE photos_users (photo_id BIGINT REFERENCES photos (id), user_id BIGINT REFERENCES users (id), PRIMARY KEY (photo_id, user_id));
INSERT INTO photos_users  (photo_id, user_id) VALUES (1, 1);