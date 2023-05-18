CREATE TABLE user(
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      username VARCHAR(64) UNIQUE,
                      password_hash VARCHAR(128)
);
CREATE TABLE role(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     title VARCHAR(64) UNIQUE
);
CREATE TABLE chat(
                     id INT PRIMARY KEY AUTO_INCREMENT,
                     name VARCHAR(64)
);


CREATE TABLE chat_user(
                          chat_user_id INT PRIMARY KEY AUTO_INCREMENT,
                          chat_id INT,
                          user_id INT,
                          role_id INT,
                          FOREIGN KEY (chat_id) REFERENCES chat (id),
                          FOREIGN KEY (user_id) REFERENCES user (id),
                          FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE messages(
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         sender_id INT,
                         chat_id INT,
                         content VARCHAR(140),
                         created_date DATE,
                         FOREIGN KEY (sender_id) REFERENCES user (id),
                         FOREIGN KEY (chat_id) REFERENCES chat (id)
);
CREATE TABLE contacts(
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          follower_id INT,
                          followed_id INT,
                          FOREIGN KEY (follower_id) REFERENCES user (id),
                          FOREIGN KEY (followed_id) REFERENCES user (id)
);

