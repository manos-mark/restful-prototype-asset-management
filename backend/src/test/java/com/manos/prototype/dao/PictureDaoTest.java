package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.ProductPicture;

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
		assertThat(pictures.get(1)).isNotNull();
		assertThat(pictures.get(1).getId()).isEqualTo(2);
		assertThat(pictures.get(1).getProduct().getId()).isEqualTo(1);
		assertThat(pictures.get(1).getPicture()).isNotNull();
	}
	
	@Test
	@Transactional
	public void getPicturesCountByProductId() {
		int count = pictureDao.getPicturesCountByProductId(1);
		assertThat(count).isEqualTo(2);
	}
	
}













