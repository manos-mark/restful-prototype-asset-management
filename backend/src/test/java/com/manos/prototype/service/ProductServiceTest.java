package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

	@Mock
	private ProductDao productDao;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProjectDao projectDao;
	
//	@Test
//	public void getProducts_success() {
//		when(productDao.getProducts())
//			.thenReturn(mockProducts);
//		
//		List<ProductDto> products = productService.getProducts();
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
//
//	@Test
//	public void getProduct_success() {
//		Product mockProduct = createMockProduct();
//		when(productDao.getProduct(1))
//			.thenReturn(mockProduct);
//		
//		Product product = productDao.getProduct(1);
//		
//		assertThat(product).isEqualTo(mockProduct);
//		assertThat(product)
//			.isEqualToComparingFieldByFieldRecursively(mockProduct);
//		verify(productDao, times(1)).getProduct(1);
//	}
	
	@Test 
	public void getProductsCount() {
		assertThatCode(() -> {
			productService.getProductsCountByStatus(2);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void deleteProduct_success() {
		Product mockProduct = createMockProduct();
		
		when(productDao.getProduct(1))
			.thenReturn(mockProduct);
		
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			productService.deleteProduct(1);
		}).doesNotThrowAnyException();
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
		Product mockProduct = createMockProduct();
		Project mockProject = createMockProject();
		
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		
		Project project = projectDao.getProject(1);
		assertThat(project).isEqualTo(mockProject);
		assertThat(project)
			.isEqualToComparingFieldByFieldRecursively(mockProject);
		assertThat(mockProject).isNotNull();
		assertThat(mockProject).hasNoNullFieldsOrProperties();
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			productService.saveProduct(mockProduct);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProduct_nullProjectFail() {
		Product mockProduct = createMockProduct();
		mockProduct.setProject(null);
		
		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> {
			productService.saveProduct(mockProduct);
		});
	}
	
	@Test
	public void saveProduct_nullProductFail() {
		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> {
			productService.saveProduct(null);
		});
	}
	
	@Test
	public void getProductsByProjectId_success() {
		List<Product> mockProducts = createMockProducts();
		Project mockProject = createMockProject();
		
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		when(productDao.getProductsByProjectId(1))
			.thenReturn(mockProducts);

		List<Product> products = productService.getProductsByProjectId(1);
		
		assertThat(products).isEqualTo(mockProducts);
		assertThat(products.get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProducts.get(0));
	}
	
	
	
	
	
	public Status createMockStatus() {
		Status mockStatus = new Status();
		mockStatus.setId(2);
		mockStatus.setStatus("NEW");
		return mockStatus;
	}
	
	public Project createMockProject() {
		Project mockProject = new Project();
		mockProject.setCompanyName("test");
		mockProject.setDate("2011-12-17 13:17:17");
		mockProject.setId(1);
		mockProject.setProjectManager("test");
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public Product createMockProduct() {
		Product mockProduct = new Product();
		mockProduct.setDate("2011-12-17 13:17:17");
		mockProduct.setDescription("test");
		mockProduct.setId(1);
		mockProduct.setProductName("test");
		mockProduct.setProject(createMockProject());
		mockProduct.setQuantity(12);
		mockProduct.setSerialNumber("test");
		mockProduct.setStatus(createMockStatus());
		return mockProduct;
	}
	
	public List<Product> createMockProducts() {
		List<Product> mockProducts = new ArrayList<Product>();
		mockProducts.add(createMockProduct());
		return mockProducts;
	}
}





















