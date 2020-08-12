
#connect to mysql and run as root user
#Create Databases
CREATE DATABASE polling;

#Create database service accounts
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';

create table polling (
  id int unsigned auto_increment not null,
  status varchar(255) not null,
  date_modified timestamp default now(),
  primary key (id)
);

#Database grants
GRANT SELECT ON polling.* to 'root'@'localhost';
GRANT INSERT ON polling.* to 'root'@'localhost';
GRANT DELETE ON polling.* to 'root'@'localhost';
GRANT UPDATE ON polling.* to 'root'@'localhost';

INSERT INTO POLLING (STATUS) VALUES (TEST STATUS 1);
INSERT INTO POLLING (STATUS) VALUES (TEST STATUS 2);
INSERT INTO POLLING (STATUS) VALUES (TEST STATUS 3);
INSERT INTO POLLING (STATUS) VALUES (TEST STATUS 4);
