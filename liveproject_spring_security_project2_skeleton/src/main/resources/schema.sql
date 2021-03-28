DROP TABLE IF EXISTS `health_profile` cascade ;
DROP TABLE IF EXISTS `health_metric` cascade ;

CREATE TABLE IF NOT EXISTS `health_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `health_metric` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `value` DOUBLE NOT NULL,
   `type` VARCHAR(45) NOT NULL,
   `profile_id` INT NOT NULL,
   PRIMARY KEY (`id`));