DROP DATABASE userdata;
CREATE DATABASE userdata;
USE userdata;

DROP USER 'sam2020'@localhost;
FLUSH PRIVILEGES;
CREATE USER 'sam2020'@'localhost' IDENTIFIED BY 'root';
GRANT ALL ON userdata.* TO 'sam2020'@'localhost';

CREATE TABLE userinfo(id INT PRIMARY KEY AUTO_INCREMENT, firstname varchar(255), lastname varchar(255), email varchar(255), pwd varchar(255), role VARCHAR(255) DEFAULT "Author");