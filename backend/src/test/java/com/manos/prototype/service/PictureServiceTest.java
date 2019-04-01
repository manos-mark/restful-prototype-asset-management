package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GenericFinder.class, GenericGateway.class})
public class PictureServiceTest {

	@Mock
	private PictureDaoImpl pictureDao;
	
	@InjectMocks
	private PictureServiceImpl pictureService;
	
	@Mock
	private GenericFinder finder;
	
	@Mock
	private ProductDaoImpl productDao;
	
	@Mock
	private GenericGateway gateway;
	
	@Test
	public void getPicturesCountByProductId_nullProduct_fail() {
		when(finder.findById(Product.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				pictureService.getPicturesByProductId(1);
			});
	}
	
	@Test
	public void getPicturesCountByProductId_success() {
		Product mockProduct = createMockProduct();
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		
		assertThatCode(() -> {
			pictureService.getPicturesByProductId(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getPicture_nullPicture_fail() {
		when(finder.findById(ProductPicture.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				pictureService.getPicture(1);
			});
	}
	
	@Test
	public void getPicture_success() {
		ProductPicture mockPicture = createMockPicture();
		
		when(finder.findById(ProductPicture.class, 1))
			.thenReturn(mockPicture);
		
		assertThatCode(() -> {
			pictureService.getPicture(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void savePicture_nullPicture_fail() {
		ProductPicture mockPicture = createMockPicture();
		mockPicture.setPicture(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				pictureService.savePicture(mockPicture);
			});
	}
	
	@Test
	public void savePicture_nullProduct_fail() {
		ProductPicture mockPicture = createMockPicture();
		mockPicture.setProduct(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				pictureService.savePicture(mockPicture);
			});
	}
	
	@Test
	public void savePicture_success() {
		ProductPicture mockPicture = createMockPicture();

		assertThatCode(() -> {
			pictureService.savePicture(mockPicture);
		}).doesNotThrowAnyException();
	}
	
	
	
	
	
	
	
	
	public ProductPicture createMockPicture() {
		ProductPicture picture = new ProductPicture();
		picture.setId(1);
		picture.setName("pic");
		picture.setProduct(createMockProduct());
		picture.setSize(1000L);
		
		byte[] b = new byte[20];
		new Random().nextBytes(b);
		picture.setPicture(b);
		return picture;
	}

	public Status createMockStatus() {
		Status mockStatus = new Status();
		mockStatus.setId(2);
		mockStatus.setStatus("NEW");
		return mockStatus;
	}
	
	public ProjectManager createMockManager() {
		ProjectManager mockManager = new ProjectManager();
		mockManager.setId(1);
		mockManager.setName("manager");
		return mockManager;
	}
	
	public Project createMockProject() {
		Project mockProject = new Project();
		mockProject.setCompanyName("test");
		mockProject.setCreatedAt(LocalDateTime.now());
		mockProject.setId(1);
		mockProject.setProjectManager(createMockManager());
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public Product createMockProduct() {
		Product mockProduct = new Product();
		mockProduct.setCreatedAt(LocalDateTime.now());
		mockProduct.setDescription("test");
		mockProduct.setId(1);
		mockProduct.setProductName("test");
		mockProduct.setProject(createMockProject());
		mockProduct.setQuantity(12);
		mockProduct.setSerialNumber("test");
		mockProduct.setStatus(createMockStatus());
		return mockProduct;
	}
}
