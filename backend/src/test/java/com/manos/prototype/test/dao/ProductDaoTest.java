package com.manos.prototype.test.dao;

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

import com.manos.prototype.dao.ProductDao;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Status;
import com.manos.prototype.test.config.AppConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/products.sql")
public class ProductDaoTest {
		
	@Autowired 
	private ProductDao productDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(productDao).isNotNull();
	}
	
	@Test
	@Transactional
	void getProducts_success() {
		List<Product> products = productDao.getProducts();
		assertThat(products).size().isEqualTo(1);
		assertThat(products).doesNotContainNull();
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
		assertThat(product.getStatusId()).isEqualTo(2);
		assertThat(product.getProjectId()).isEqualTo(1);
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
		assertThat(product.getStatusId()).isEqualTo(2);
		assertThat(product.getProjectId()).isEqualTo(1);
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
	
//	@Test
//	@Transactional
//	void saveProduct_success() {
//		Status status = new Status();
//		status.setId(1);
//		
//		Product product = new Product();
//		product.setCompanyName("test");
//		product.setProductName("test");
//		product.setProductManager("test");
//		product.setDate("2019-12-17 14:14:14");
//		product.setStatus(status);
//		
//		assertThatCode(() -> {
//			productDao.saveProduct(product);
//		}).doesNotThrowAnyException();
//		
//		Product savedProduct = productDao.getProduct(2);
//		assertThat(savedProduct).isNotNull();
//		assertThat(savedProduct.getCompanyName()).isEqualTo("test");
//		assertThat(savedProduct.getProductManager()).isEqualTo("test");
//		assertThat(savedProduct.getProductName()).isEqualTo("test");
//		assertThat(savedProduct.getDate()).isEqualTo("2019-12-17 14:14:14");
//		assertThat(savedProduct.getStatus().getId()).isEqualTo(1);
//	}
	
	@Test
	@Transactional
	void saveProduct_fail() {
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> { 
			productDao.saveProduct(null); 
		});
	}
	
}











