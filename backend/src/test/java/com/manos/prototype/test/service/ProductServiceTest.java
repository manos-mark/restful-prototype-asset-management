package com.manos.prototype.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.ProductDao;
import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	List<Product> mockProducts;
	Product product;
	Project project;
	Status status;
	
	@Mock
	private ProductDao productDao;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProjectDao projectDao;
	
	@Before
	public void init() {
		mockProducts = new ArrayList<Product>();
		product = new Product();
		project = new Project();
		status = new Status();
		
		status.setId(2);
		status.setStatus("NEW");
		
		project.setCompanyName("test");
		project.setDate("2011-12-17 13:17:17");
		project.setId(1);
		project.setProjectManager("test");
		project.setProjectName("test");
		project.setStatus(status);
		
		product.setDate("2011-12-17 13:17:17");
		product.setDescription("test");
		product.setId(1);
		product.setProductName("test");
		product.setProject(project);
		product.setQuantity(12);
		product.setSerialNumber("test");
		product.setStatus(status);
		mockProducts.add(product);
		
	}
	
//	@Test
//	public void getProducts_success() {
//		List<ProductDto> products;
//		when(productDao.getProducts())
//			.thenReturn(mockProducts);
//		
//		products = productService.getProducts();
//		assertThat(products).isEqualTo(mockProducts);
//		assertThat(products.get(0))
//			.isEqualToComparingFieldByFieldRecursively(mockProducts.get(0));
//		
//		verify(productDao, times(1)).getProducts();
//	}
//	
//	@Test
//	public void getProducts_emptyProductsFail() {
//		mockProducts = new ArrayList<Product>();
//		when(productDao.getProducts())
//			.thenReturn(mockProducts);
//		
//		assertThatExceptionOfType(EntityNotFoundException.class)
//			.isThrownBy(() -> {
//				productService.getProducts();
//			});
//	}
//	
//	@Test
//	public void getProducts_nullProductsFail() {
//		when(productDao.getProducts())
//			.thenReturn(null);
//		
//		assertThatExceptionOfType(NullPointerException.class)
//			.isThrownBy(() -> {
//				productService.getProducts();
//			});
//	}

	@Test
	public void getProduct_success() {
		when(productDao.getProduct(1))
			.thenReturn(product);
		
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThat(productDao.getProduct(1).getDate())
			.isEqualTo("2011-12-17 13:17:17");
		assertThat(productDao.getProduct(1).getDescription())
			.isEqualTo("test");
		assertThat(productDao.getProduct(1).getProductName())
			.isEqualTo("test");
		assertThat(productDao.getProduct(1).getSerialNumber())
			.isEqualTo("test");
		assertThat(productDao.getProduct(1).getQuantity())
			.isEqualTo(12);
		assertThat(productDao.getProduct(1).getId())
			.isEqualTo(1);
		assertThat(productDao.getProduct(1).getProject())
			.isEqualTo(project);
		assertThat(productDao.getProduct(1).getStatus())
			.isEqualTo(status);
		assertThat(productDao.getProduct(1).getProject())
			.hasNoNullFieldsOrProperties();
		assertThat(productDao.getProduct(1).getStatus())
			.hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			productDao.getProduct(1);
		}).doesNotThrowAnyException();
		verify(productDao, times(11)).getProduct(1);
	}
	
//	@Test
//	public void getProduct_nullProductFail() {
//		when(productDao.getProduct(1))
//			.thenReturn(null);
//		
//		assertThatExceptionOfType(EntityNotFoundException.class)
//			.isThrownBy(() -> {
//				productService.getProduct(1);
//			});
//	}
	
	@Test
	public void deleteProduct_success() {
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			productDao.deleteProduct(1);
		}).doesNotThrowAnyException();
		verify(productDao, times(1)).deleteProduct(1);
	}
	
	@Test
	public void deleteProduct_nullProductFail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.deleteProduct(1);
			});
	}
	
	@Test
	public void saveProduct_success() {
		when(projectDao.getProject(1))
			.thenReturn(project);
		
		assertThat(project).isNotNull();
		assertThat(project).hasNoNullFieldsOrProperties();
		assertThat(product).isNotNull();
		assertThat(product).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			productService.saveProduct(product);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProduct_nullProjectFail() {
		when(projectDao.getProject(1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			productService.saveProduct(product);
		});
	}
	
	@Test
	public void saveProduct_nullProductFail() {
		when(projectDao.getProject(1))
			.thenReturn(project);

		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> {
			productService.saveProduct(null);
		});
	}
	
	@Test
	public void getProductsByProjectId_success() {
		
	}
}





















