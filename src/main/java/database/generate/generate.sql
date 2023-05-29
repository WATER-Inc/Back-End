INSERT INTO user(username, password_hash) VALUES
                                              ('user1', 'hash1'),
                                              ('user2', 'hash2'),
                                              ('user3', 'hash3'),
                                              ('user4', 'hash4'),
                                              ('user5', 'hash5'),
                                              ('user6', 'hash6'),
                                              ('user7', 'hash7'),
                                              ('user8', 'hash8'),
                                              ('user9', 'hash9'),
                                              ('user10', 'hash10');

INSERT INTO chat(name) VALUES
                           ('chat1'),
                           ('chat2'),
                           ('chat3');

INSERT INTO role(title)
VALUES ('admin'),
       ('moderator'),
       ('user');

INSERT INTO chat_user(chat_id, user_id, role_id) VALUES
                                                     (1, 1, 1),
                                                     (1, 2, 2),
                                                     (1, 3, 3),
                                                     (1, 4, 3),
                                                     (1, 5, 3),
                                                     (2, 3, 1),
                                                     (2, 6, 2),
                                                     (2, 7, 3),
                                                     (3, 2, 1),
                                                     (3, 5, 2),
                                                     (3, 8, 3),
                                                     (3, 9, 3),
                                                     (3, 10, 3);


INSERT INTO messages(sender_id, chat_id, content, created_date) VALUES
                                                                    (1, 1, 'Hello, everyone!', '2023-05-01'),
                                                                    (2, 1, 'Hey there!', '2023-05-02'),
                                                                    (3, 1, 'How are you all doing today?', '2023-05-03'),
                                                                    (4, 1, 'I have a question about the project we are working on...', '2023-05-04'),
                                                                    (5, 1, 'I think we should have a meeting to discuss...', '2023-05-05'),
                                                                    (3, 2, 'Welcome to the new chat room!', '2023-05-06'),
                                                                    (6, 2, 'Thanks for inviting me!', '2023-05-07'),
                                                                    (7, 2, 'I am looking forward to collaborating with you all.', '2023-05-08'),
                                                                    (2, 3, 'Can someone help me with this issue I am having?', '2023-05-09'),
                                                                    (5, 3, 'Sure, what do you need help with?', '2023-05-10'),
                                                                    (8, 3, 'I might be able to assist as well.', '2023-05-11'),
                                                                    (9, 3, 'I am not sure, but I can take a look.', '2023-05-12'),
                                                                    (10, 3, 'Let me know if you need any further assistance.', '2023-05-13');