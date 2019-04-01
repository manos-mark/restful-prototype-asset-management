package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.Product;
import com.manos.prototype.search.ProductSearch;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
@Sql(scripts = "classpath:/sql/pictures.sql")
public class ProductDaoTest {
		
	@Autowired 
	private ProductDaoImpl productDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(productDao).isNotNull();
	}
	
	@Test
	@Transactional
	void getProducts_success() {
		List<OrderClause> orderClauses = new ArrayList<>();
		OrderClause clause1 = new OrderClause();
		clause1.setDirection(OrderDirection.DESCENDING);
		clause1.setField(null);
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(10);
		pageRequest.setOrderClauses(orderClauses);
		
		ProductSearch search = new ProductSearch();
		
		List<Product> products = productDao.getProducts(pageRequest, search);
		Product product = products.get(0);
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
		assertThat(products).size().isEqualTo(1);
		assertThat(products).doesNotContainNull();
		assertThat(product.getThumbPicture().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void search() {
		List<Product> products = productDao.search("productName", "productName");
		assertThat(products).isNotNull();
	}
	
	@Test
	@Transactional
	void getProductsByProjectId_success() {
		List<Product> products = productDao.getProductsByProjectId(1);
		Product product = products.get(0);
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
		assertThat(products).size().isEqualTo(1);
		assertThat(products).doesNotContainNull();
		assertThat(product.getThumbPicture().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void getProductByName() {
		Product product = productDao.getProductByName("productName");
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
		assertThat(product.getThumbPicture().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void getProductBySerialNumber() {
		Product product = productDao.getProductBySerialNumber("serialNumber");
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(product.getProductName()).isEqualTo("productName");
		assertThat(product.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(product.getDescription()).isEqualTo("description");
		assertThat(product.getQuantity()).isEqualTo(12);
		assertThat(product.getStatus().getId()).isEqualTo(2);
		assertThat(product.getProject().getId()).isEqualTo(1);
		assertThat(product.getThumbPicture().getId()).isEqualTo(2);
	}
	
	@Test
	@Transactional
	void getProductsByProjectId_fail() {
		List<Product> products = productDao.getProductsByProjectId(100);
		assertThat(products).isEmpty();
	}
	
	@Test
	@Transactional
	void getProductsCount() {
		assertThat(productDao.getProductsCountByStatus(2)).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getProductsCountByProjectId() {
		assertThat(productDao.getProductsCountByProjectId(1)).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void count() {
		assertThat(productDao.count(new ProductSearch())).isEqualTo(1);
	}
}











