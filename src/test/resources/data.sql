INSERT INTO USERS (first_name, last_name, admin, email, password)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('Denis', 'Ndjanda', false, 'denis@gmail.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'),
       ('Lou', 'Malou', false, 'lou@gmail.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq');
       
INSERT INTO TEACHERS (id, first_name, last_name)
VALUES (1, 'Paul', 'ATREIDIS'),
       (2, 'Marie', 'CROISSANT'),
       (3, 'Hans', 'HARTUNG'),
       (4, 'Sam', 'SOMG');

INSERT INTO SESSIONS (id, name, description, date, teacher_id)
VALUES 	(11, 'Yoga du soir', 'le yoga du soir', '2026-01-01', 1),
		(12, 'Yoga du matin', 'le yoga du matin', '2026-02-02', 2),
		(13, 'Veneration du sol', 'la veneration du sol', '2026-03-03', 3);