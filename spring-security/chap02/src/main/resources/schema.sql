DROP TABLE IF EXISTS users;
CREATE TABLE wzb_users
(
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  TINYINT      NOT NULL DEFAULT 1,
    name VARCHAR(50)  NULL,
    PRIMARY KEY (username)
) ENGINE = INNODB;

DROP TABLE IF EXISTS authorities;
CREATE TABLE wzb_authorities(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES wzb_users (username)
) ENGINE=InnoDB;

CREATE UNIQUE INDEX ix_auth_username
on wzb_authorities(username, authority);