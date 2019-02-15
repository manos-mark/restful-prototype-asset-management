package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {

	@Mock
	private PictureDaoImpl pictureDao;
	
	@InjectMocks
	private PictureServiceImpl pictureService;
	
	@Mock
	private ProductDaoImpl productDao;
	
	@Test
	public void getPicturesCountByProductId() {
		assertThatCode(() -> {
			pictureService.getPicturesCountByProductId(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void deletePicture_success() {
		ProductPicture picture = createMockPicture();
	}
	
	
	private ProductPicture createMockPicture() throws IOException {
		BufferedImage img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		
		ProductPicture pic = new ProductPicture();
		pic.setId(2);
		pic.setProduct(createMockProduct());
		pic.setPicture(baos.toByteArray());
		return null;
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
}
