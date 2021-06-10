create table users (
id integer NOT NULL AUTO_INCREMENT,
first_name varchar(255) not null,
last_name varchar(255) not null,
email_address varchar(50) not null,
created_at timestamp not null,
updated_at timestamp,
primary key (id));