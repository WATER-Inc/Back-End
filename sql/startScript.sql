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
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          chat_id INT,
                          user_id INT,
                          role_id INT,
                          FOREIGN KEY (chat_id) REFERENCES chat (id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
                          FOREIGN KEY (role_id) REFERENCES role (id)
);

ALTER TABLE chat_user ADD UNIQUE (user_id, chat_id);

CREATE TABLE messages(
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         sender_id INT,
                         chat_id INT,
                         content VARCHAR(140),
                         created_date DATETIME,
                         FOREIGN KEY (sender_id) REFERENCES user (id) ON DELETE CASCADE,
                         FOREIGN KEY (chat_id) REFERENCES chat (id) ON DELETE CASCADE
);