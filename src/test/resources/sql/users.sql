insert into roles(id, created, updated, name) values (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'ROLE_USER');
insert into users(id, created, updated, username, email, password) values (1, '2022-01-01 00:00:00', '2022-01-01 00:00:00', 'test', 'test@test.ru', '$2a$10$Xa61OILEnjOgqOxaw1pCae4BxNblwECgAVoWWf0jWfvA4wf6AUCAC');
insert into user_roles(role_id, user_id) values (1, 1);