INSERT INTO users (first_name, is_active, last_name, password, username)
VALUES ('admin', true, 'admin', '$2a$10$YguKinxXFVtNxnupPCkuOe90N53dr7ofp1HKZqZGA2nP0rG3479XW', 'admin');

INSERT INTO users (first_name, is_active, last_name, password, username)
VALUES ('admin', true, 'admin', '$2a$10$YguKinxXFVtNxnupPCkuOe90N53dr7ofp1HKZqZGA2nP0rG3479XW', 'admin1');

INSERT INTO training_types (id, training_type_name)
VALUES (1, 'BOXING');

INSERT INTO training_types (id, training_type_name)
VALUES (2, 'KARATE');


INSERT INTO trainee (address, date_of_birth, user_id) VALUES ('Tbilisi', '2000-01-01', 1);
INSERT INTO trainer (user_id, specialization_id) VALUES (2, 1);

INSERT INTO trainings (duration, training_date, training_name, trainee_id, trainer_id, training_type_id)
VALUES(1, '2024-01-01', 'Boxing Session', 1, 2, 1)