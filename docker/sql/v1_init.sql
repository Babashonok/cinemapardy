CREATE SCHEMA IF NOT EXISTS `cinemapardy`;
USE `cinemapardy`;
CREATE TABLE 'cinema' (
  cinema_id VARCHAR(36) NOT NULL,
  title VARCHAR(256) NOT NULL,
  PRIMARY KEY (cinema_id)
);
