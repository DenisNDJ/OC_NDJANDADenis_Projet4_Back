INSERT INTO USERS (first_name, last_name, admin, email, password)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('Denis', 'Ndjanda', false, 'denis@gmail.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('Lou', 'Malou', false, 'lou@gmail.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq');
       
INSERT INTO TEACHERS (first_name, last_name)
VALUES ('Paul', 'ATREIDIS'),
       ('Marie', 'CROISSANT'),
       ('Hans', 'HARTUNG');

INSERT INTO SESSIONS (name, description, date, teacher_id)
VALUES 	('Yoga du soir', 'le yoga du soir', '2026-01-01 00:00:00', 0),
		('Yoga du matin', 'le yoga du matin', '2026-02-02 00:00:00', 2),
		('Veneration du sol', 'la veneration du sol', '2026-03-03 00:00:00', 4);