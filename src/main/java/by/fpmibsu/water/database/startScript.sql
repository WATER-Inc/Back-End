
CREATE TABLE user(
                      user_id INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(64),
                      email VARCHAR(120),
                      password_hash VARCHAR(128),
                      last_seen DATE,
                      about_me VARCHAR(120)
);
CREATE TABLE role(
                     role_id INT PRIMARY KEY AUTO_INCREMENT,
                     user_id INT,
                     title VARCHAR(64)
);
CREATE TABLE chat(
                     chat_id INT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(64)
);

CREATE TABLE user_chat_link(
                               user_chat_link_id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT,
                               chat_id INT,
                               FOREIGN KEY (chat_id) REFERENCES chat (chat_id),
                               FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE chat_user(
                          chat_user_id INT PRIMARY KEY AUTO_INCREMENT,
                          chat_id INT,
                          user_id INT,
                          role_id INT,
                          FOREIGN KEY (chat_id) REFERENCES chat (chat_id),
                          FOREIGN KEY (user_id) REFERENCES user (user_id),
                          FOREIGN KEY (role_id) REFERENCES role (role_id)
);



CREATE TABLE messages(
                         message_id INT PRIMARY KEY AUTO_INCREMENT,
                         sender_id INT,
                         chat_id INT,
                         content VARCHAR(140),
                         created_date DATE,
                         FOREIGN KEY (sender_id) REFERENCES user (user_id),
                         FOREIGN KEY (chat_id) REFERENCES chat (chat_id)
);

CREATE TABLE post(
                     post_id INT PRIMARY KEY AUTO_INCREMENT,
                     content VARCHAR(140),
                     created_date DATE,
                     user_id INT,
                     FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE followers(
                          followers_id INT PRIMARY KEY AUTO_INCREMENT,
                          follower_id INT,
                          followed_id INT,
                          FOREIGN KEY (follower_id) REFERENCES user (user_id),
                          FOREIGN KEY (followed_id) REFERENCES user (user_id)
);

CREATE TABLE comments(
                         comment_id INT PRIMARY KEY AUTO_INCREMENT,
                         content VARCHAR(120),
                         post_id INT,
                         author_id INT,
                         created_date DATE,
                         FOREIGN KEY (author_id) REFERENCES user (user_id),
                         FOREIGN KEY (post_id) REFERENCES post (post_id)
);