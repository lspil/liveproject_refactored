DROP TABLE IF EXISTS `authority` cascade ;
DROP TABLE IF EXISTS `user` cascade ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` TEXT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `authority` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`));