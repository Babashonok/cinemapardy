CREATE SCHEMA IF NOT EXISTS `cinemapardy`;
USE `cinemapardy`;
CREATE TABLE cinema (
  cinema_id VARCHAR(36) NOT NULL,
  title VARCHAR(256) NOT NULL,
  created_date DATETIME NULL,
  director VARCHAR(256) NULL,
  imdb_rank DECIMAL(16,5) NULL,
  PRIMARY KEY (cinema_id)
);
