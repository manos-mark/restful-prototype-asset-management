DROP DATABASE IF EXISTS `prototype-db`;
CREATE DATABASE  IF NOT EXISTS `prototype-db`;
USE `prototype-db`;

--
-- Table structure for (enum) table `e_status`
--
CREATE TABLE `e_status` (
	`id` INT(11) NOT NULL,
	`status` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`id`)
);
INSERT INTO `e_status` VALUES
 	(1, 'In progress'), (2, 'New'), (3, 'Finished');

--
-- Table structure for table `project`
--
CREATE TABLE `project` (
	`id` INT(11) NOT NULL,
	`project_name` VARCHAR(45) NOT NULL,
	`company_name` VARCHAR(45) NOT NULL,
	`project_manager` VARCHAR(45) NOT NULL,
	`date` DATE NOT NULL,
	`status_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`status_id`)
		REFERENCES e_status (`id`)
);

--
-- Table structure for table `product`
--
CREATE TABLE `product` (
	`id` INT(11) NOT NULL,
	`date` DATE NOT NULL,
	`product_name` VARCHAR(45) NOT NULL,
	`serial_number` VARCHAR(45) NOT NULL,
	`description` VARCHAR(500) NOT NULL,
	`quantity` INT(11) NOT NULL,
	`status_id` INT(11) NOT NULL,
	`project_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`status_id`)
		REFERENCES e_status (`id`),
	FOREIGN KEY (`project_id`)
		REFERENCES project (`id`)	
);

--
-- Table structure for (enum) table `e_action`
--
CREATE TABLE `e_action` (
	`id` INT(11) NOT NULL,
	`action` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`id`)
);
INSERT INTO `e_action` VALUES
 	(1, 'LOGGED_IN'), (2, 'CREATED_PROJECT'), (3, 'UPDATED_PROJECT'), (4, 'DELETED_PROJECT');
INSERT INTO `e_action` VALUES
 	(5, 'CREATED_PROJECT'), (6, 'UPDATED_PROJECT'), (7, 'DELETED_PROJECT'), (8, 'LOGGED_OUT');
--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` char(80) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(254) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--
-- NOTE: The passwords are encrypted using BCrypt
--
-- Default passwords here are: fun123
--
INSERT INTO `user` (password,first_name,last_name,email)
VALUES 
('$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','John','Doe','john@luv2code.com'),
('$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Mary','Public','mary@luv2code.com'),
('$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Susan','Adams','susan@luv2code.com');

--
-- Table structure for table `activity`
--
CREATE TABLE `activity` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`action_id` INT(11) NOT NULL,
	`user_id` INT(11) NOT NULL,
	`date` DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`action_id`) 
		REFERENCES e_action (`id`),
	FOREIGN KEY (`user_id`) 
		REFERENCES user (`id`)
);
INSERT INTO `activity` (id,action_id,user_id,date) VALUES
(1,1,1,'2011-12-17 13:17:17'), (2,8,1,'2011-12-17 14:17:17'), (3,1,2,'2011-12-17 13:17:17');