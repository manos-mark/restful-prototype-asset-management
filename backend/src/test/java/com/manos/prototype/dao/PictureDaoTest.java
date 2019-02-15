package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Product;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
@Sql(scripts = "classpath:/sql/pictures.sql")
public class PictureDaoTest {

	@Autowired
	private PictureDaoImpl pictureDao;
	
	@Test
	@Transactional
	public void autowiredDao_success() {
		assertThat(pictureDao).isNotNull();
	}
	
	@Test
	@Transactional
	public void getPicturesByProductId() {
		List<ProductPicture> pictures = pictureDao.getPicturesByProductId(1);
		assertThat(pictures.get(0)).isNotNull();
		assertThat(pictures.get(0).getId()).isEqualTo(1);
		assertThat(pictures.get(0).getProduct().getId()).isEqualTo(1);
		assertThat(pictures.get(0).getPicture()).isNotNull();
	}
	
	@Test
	@Transactional
	public void getPicture() {
		ProductPicture picture = pictureDao.getPicture(1);
		assertThat(picture.getId()).isEqualTo(1);
		assertThat(picture.getProduct().getId()).isEqualTo(1);
		assertThat(picture.getPicture()).isNotNull();
	}
	
	@Test
	@Transactional
	public void savePicture() throws IOException {
		BufferedImage img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		
		Product product = new Product();
		product.setId(1);
		
		ProductPicture picture = new ProductPicture();
		picture.setPicture(baos.toByteArray());
		picture.setProduct(product);
		picture.setId(2);
		
		assertThatCode(() -> { 
			pictureDao.savePicture(picture);
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	public void deletePicture() {
		assertThatCode(() -> { 
			pictureDao.deletePicture(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	public void getPicturesCountByProductId() {
		Long count = pictureDao.getPicturesCountByProductId(1);
		assertThat(count).isEqualTo(1);
	}
}













