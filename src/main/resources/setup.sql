DROP DATABASE userdata;
CREATE DATABASE userdata;
USE userdata;

DROP USER 'sam2020'@localhost;
FLUSH PRIVILEGES;
CREATE USER 'sam2020'@'localhost' IDENTIFIED BY 'root';
GRANT ALL ON userdata.* TO 'sam2020'@'localhost';

CREATE TABLE userinfo(id INT PRIMARY KEY AUTO_INCREMENT, firstname varchar(255), lastname varchar(255), email varchar(255), pwd varchar(255), role VARCHAR(255) DEFAULT "Author");
CREATE TABLE submission
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    author_contact varchar(255) null,
    format varchar(255) null,
    title varchar(255) null,
    version varchar(255) null,
    constraint submission_id_unidex
        unique(id),
    state VARCHAR(255))

create table review
(
	id int auto_increment,
	reviewerId int null,
	paperId int null,
	rating int null,
	comments VARCHAR(255) null,
    state VARCHAR(255),
	constraint review_pk
		primary key (id),
	constraint paperId
		foreign key (paperId) references submission (id),
	constraint reviewerId
		foreign key (reviewerId) references userinfo (id)
);