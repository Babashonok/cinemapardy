-- -----------------------------------------------------
-- Schema cinemapardy
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cinemapardy`;
-- -----------------------------------------------------
-- Schema cinemapardy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinemapardy` DEFAULT CHARACTER SET utf8;

GRANT ALL privileges on cinemapardy.* to "app_user" identified by "testpw1234";
FLUSH PRIVILEGES;

