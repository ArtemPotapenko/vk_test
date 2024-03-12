CREATE TABLE account (
                         id int PRIMARY KEY AUTO_INCREMENT,
                         username varchar(80) UNIQUE,
                         password varchar(80)
);
CREATE TABLE role(
    id int PRIMARY KEY AUTO_INCREMENT,
    name varchar(80) unique
);
CREATE TABLE account_role(
    role_id int,
    account_id int,
    foreign key (role_id) references role(id),
    foreign key (account_id) references account(id)
);
CREATE TABLE audit(
  id int primary key auto_increment,
  username varchar(80),
  create_date datetime,
  parameters text,
  has_access bool
);

INSERT INTO role(name) VALUES ('ADMIN');
INSERT INTO role(name) VALUES ('POSTS_VIEWER');
INSERT INTO role(name) VALUES ('POSTS_EDITOR');
INSERT INTO role(name) VALUES ('ALBUMS_VIEWER');
INSERT INTO role(name) VALUES ('ALBUMS_EDITOR');
INSERT INTO role(name) VALUES ('USERS_VIEWER');
INSERT INTO role(name) VALUES ('USERS_EDITOR');

INSERT INTO account(username, password) VALUES ( 'admin', '$2a$10$UYT9sVcVEDuynEVwRVJn9OpMpG5eDx382AD.SlBj2rWQdnw5hLSSe');
INSERT INTO account_role(role_id, account_id) VALUES (1,1);