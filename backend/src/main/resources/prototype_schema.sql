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
INSERT INTO `e_status` (id,status) VALUES
 	(1, 'IN_PROGRESS'), (2, 'NEW'), (3, 'FINISHED');

--
-- Table structure for table `project`
--
CREATE TABLE `project` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`project_name` VARCHAR(45) NOT NULL,
	`company_name` VARCHAR(45) NOT NULL,
	`project_manager` VARCHAR(45) NOT NULL,
	`date` DATE NOT NULL,
	`status_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`status_id`)
		REFERENCES e_status (`id`)
);
INSERT INTO `project` (project_name,company_name,project_manager,date,status_id) VALUES
('firstProject', 'firstCompany', 'firstProjectManager', '2011-12-17', 2);
--
-- Table structure for table `product`
--
CREATE TABLE `product` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
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
INSERT INTO `product` (date,product_name,serial_number,description,quantity,status_id,project_id) VALUES
	('2011-12-17','productName','serialNumber','description',12,2,1);
    
--
-- Table structure for `product_pictures`
--
CREATE TABLE `product_pictures` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `picture` MEDIUMBLOB NOT NULL,
    `product_id` INT(11) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `size` LONG NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_id`)
		REFERENCES product (`id`)
);

INSERT INTO `product_pictures` (picture, product_id, name, size) VALUES 
	(x'89504E470D0A1A0A0000000D494844520000001000000010080200000090916836000000017352474200AECE1CE90000000467414D410000B18F0BFC6105000000097048597300000EC300000EC301C76FA8640000001E49444154384F6350DAE843126220493550F1A80662426C349406472801006AC91F1040F796BD0000000049454E44AE426082',1, 'pic1.jpg', 2000);
INSERT INTO `product_pictures` (picture, product_id, name, size) VALUES 
	(x'89504E470D0A1A0A0000000D494844520000001000000010080200000090916836000000017352474200AECE1CE90000000467414D410000B18F0BFC6105000000097048597300000EC300000EC301C76FA8640000001E49444154384F6350DAE843126220493550F1A80662426C349406472801006AC91F1040F796BD0000000049454E44AE426082',1, 'pic2.png', 4000);

CREATE TABLE `product_thumb_picture` (
    `product_id` INT(11) NOT NULL,
    `thumb_picture_id` INT(11) NOT NULL,
    PRIMARY KEY (`product_id`),
    FOREIGN KEY (`product_id`, `thumb_picture_id`)
		REFERENCES product_pictures (`product_id`, `id`)
);
INSERT INTO `product_thumb_picture` (product_id, thumb_picture_id) VALUES
	(1,2);
--
-- Table structure for (enum) table `e_action`
--
CREATE TABLE `e_activity_action` (
	`id` INT(11) NOT NULL,
	`action` VARCHAR(15) NOT NULL,
	PRIMARY KEY (`id`)
);
INSERT INTO `e_activity_action` (id,action) VALUES
 	(1, 'LOGGED_IN'), (2, 'CREATED_PROJECT'), (3, 'UPDATED_PROJECT'), (4, 'DELETED_PROJECT');
INSERT INTO `e_activity_action` (id,action) VALUES
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
INSERT INTO `user` (password,first_name,last_name,email) VALUES 
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
		REFERENCES e_activity_action (`id`),
	FOREIGN KEY (`user_id`) 
		REFERENCES user (`id`)
);
INSERT INTO `activity` (id,action_id,user_id,date) VALUES
	(1,1,1,'2011-12-17 13:17:17'), (2,8,1,'2011-12-17 14:17:17'), (3,1,2,'2011-12-17 13:17:17');