INSERT INTO `product_pictures` (id,picture, product_id, name, size) VALUES 
	(1, FILE_READ('classpath:/images/pic1.jpg'),1, 'pic1.jpg', 4000);
INSERT INTO `product_pictures` (id,picture, product_id, name, size) VALUES 
	(2, FILE_READ('classpath:/images/pic2.png'),1, 'pic2.png', 4000);
INSERT INTO `product_pictures` (id,picture, product_id, name, size) VALUES 
	(3, FILE_READ('classpath:/images/pic3.gif'),1, 'pic3.gif', 4000);

INSERT INTO `product_thumb_picture` (product_id, thumb_picture_id) VALUES
	(1,2);