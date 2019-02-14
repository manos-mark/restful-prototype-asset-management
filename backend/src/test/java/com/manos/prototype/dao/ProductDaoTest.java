package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
public class ProductDaoTest {
		
	@Autowired 
	private ProductDaoImpl productDao;
	
	@Autowired
	private ProjectDaoImpl projectDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(productDao).isNotNull();
	}
	
	@Test
	@Transactional
	void getProduct_success() {
		Product product = productDao.getProduct(1);
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getDate()).isEqualTo("2011-12-17");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getProduct_fail() {
		assertThatExceptionOfType(NoResultException.class)
			.isThrownBy(() -> { 
				productDao.getProduct(100); 
			});
	}
	
	@Test
	@Transactional
	void getProductsByProjectId_success() {
		List<Product> products = productDao.getProductsByProjectId(1);
		Product product = products.get(0);
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getDate()).isEqualTo("2011-12-17");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
		assertThat(products).size().isEqualTo(1);
		assertThat(products).doesNotContainNull();
	}
	
	@Test
	@Transactional
	void getProductsByProjectId_fail() {
		List<Product> products = productDao.getProductsByProjectId(100);
		assertThat(products).isEmpty();
	}
	
	@Test
	@Transactional
	void deleteProduct_success() {
		assertThatCode(() -> { 
			productDao.deleteProduct(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void deleteProduct_fail() {
		assertThatExceptionOfType(NoResultException.class)
			.isThrownBy(() -> { 
				productDao.deleteProduct(1000);
			});
	}
	
	@Test
	@Transactional
	void saveProduct_success() {
		Project project = projectDao.getProject(1);
		assertThat(project).isNotNull();
		assertThat(project).hasNoNullFieldsOrProperties();
		
		Product product = new Product();
		product.setProductName("test");
		product.setDate("2019-12-17 14:14:14");
		product.setDescription("test");
		product.setProject(project);
		product.setQuantity(122);
		product.setSerialNumber("test");
		product.setStatus(new Status(2));
		
		assertThatCode(() -> {
			productDao.saveProduct(product);
		}).doesNotThrowAnyException();
		
		Product savedProduct = productDao.getProduct(2);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct).hasNoNullFieldsOrProperties();
		assertThat(savedProduct.getProductName()).isEqualTo("test");
		assertThat(savedProduct.getDate()).isEqualTo("2019-12-17 14:14:14");
		assertThat(savedProduct.getDescription()).isEqualTo("test");
		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
		assertThat(savedProduct.getQuantity()).isEqualTo(122);
		assertThat(savedProduct.getSerialNumber()).isEqualTo("test");
		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void updateProduct_success() {
		Project project = projectDao.getProject(1);
		assertThat(project).isNotNull();
		assertThat(project).hasNoNullFieldsOrProperties();
		
		Product product = new Product();
		product.setProductName("test");
		product.setDate("2019-12-17 14:14:14");
		product.setDescription("test");
		product.setProject(project);
		product.setQuantity(122);
		product.setSerialNumber("test");
		product.setStatus(new Status(2));
		product.setId(1);
		
		assertThatCode(() -> {
			productDao.updateProduct(product);
		}).doesNotThrowAnyException();
		
		Product savedProduct = productDao.getProduct(1);
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct).hasNoNullFieldsOrProperties();
		assertThat(savedProduct.getProductName()).isEqualTo("test");
		assertThat(savedProduct.getDate()).isEqualTo("2019-12-17 14:14:14");
		assertThat(savedProduct.getDescription()).isEqualTo("test");
		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
		assertThat(savedProduct.getQuantity()).isEqualTo(122);
		assertThat(savedProduct.getSerialNumber()).isEqualTo("test");
		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void saveProduct_fail() {
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> { 
			productDao.saveProduct(null); 
		});
	}
	
	@Test
	@Transactional
	void getProductsCount() {
		assertThat(productDao.getProductsCountByStatus(2)).isEqualTo(1);
	}
}











