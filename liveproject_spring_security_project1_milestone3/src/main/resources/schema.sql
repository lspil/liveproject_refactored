DROP TABLE IF EXISTS `authority` cascade ;
DROP TABLE IF EXISTS `user` cascade ;
DROP TABLE IF EXISTS `client_grant_types` cascade ;
DROP TABLE IF EXISTS `client` cascade ;

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

CREATE TABLE IF NOT EXISTS `client` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `client_id` VARCHAR(45) NOT NULL,
  `secret` TEXT NOT NULL,
  `scope` VARCHAR(45) NOT NULL,
  `rediect_uri` TEXT NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `client_grant_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `grant_type` VARCHAR(45) NOT NULL,
  `client_id` INT NOT NULL,
  PRIMARY KEY (`id`));

