
ALTER USER debezium IDENTIFIED WITH mysql_native_password BY 'password';

        GRANT RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO debezium;
GRANT SELECT, INSERT, UPDATE, DELETE ON company.* TO debezium;


CREATE DATABASE company;

CREATE TABLE company.user (
                              id INT PRIMARY KEY NOT NULL,
                              first_name VARCHAR(50),
                              last_name VARCHAR(50)
);


INSERT INTO company.user (id, first_name, last_name) VALUES (100, 'John', 'Doe');
UPDATE company.user SET last_name = 'Oliver' WHERE id = 100;