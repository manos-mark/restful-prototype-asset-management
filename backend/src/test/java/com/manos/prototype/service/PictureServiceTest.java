package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.manos.prototype.exception.EntityNotFoundException;

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
		when(pictureDao.getPicturesCountByProductId(1))
			.thenReturn(2L);
		
		assertThatCode(() -> {
			pictureService.getPicturesCountByProductId(1);
		}).doesNotThrowAnyException();
		
		assertThat(pictureDao.getPicturesCountByProductId(1))
			.isEqualTo(2);
	}
	
	@Test
	public void savePicture_success() throws IOException {
		ProductPicture mockPicture = createMockPicture();
		
		assertThatCode(() -> {
			pictureService.savePicture(mockPicture);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void addPicture_success() throws IOException {
		ProductPicture mockPicture = createMockPicture();
		mockPicture.setId(0);
		assertThatCode(() -> {
			pictureService.savePicture(mockPicture);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void savePicture_nullPic_fail() throws IOException {
		ProductPicture mockPicture = createMockPicture();
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			mockPicture.setPicture(null);
			pictureService.savePicture(mockPicture);
		});
	}
	
	@Test
	public void savePicture_nullProd_fail() throws IOException {
		ProductPicture mockPicture = createMockPicture();
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			mockPicture.setProduct(null);
			pictureService.savePicture(mockPicture);
		});
	}
	
	@Test
	public void getPicturesByProductId_success() throws IOException {
		Product mockProduct = createMockProduct();
		ProductPicture mockPic = createMockPicture();
		List<ProductPicture> mockList = new ArrayList<>();
		mockList.add(mockPic);
		
		when(productDao.getProduct(1))
			.thenReturn(mockProduct);
		when(pictureDao.getPicturesByProductId(1))
			.thenReturn(mockList);
		
		List<ProductPicture> picList = pictureService.getPicturesByProductId(1);
		assertThat(picList).size().isEqualTo(1);
		assertThat(picList.get(0)).isEqualTo(mockList.get(0));
		assertThat(picList.get(0)).isEqualToComparingFieldByFieldRecursively(mockList.get(0));
	}
	
	@Test
	public void getPicturesByProductId_nullProduct_fail() throws IOException {
		ProductPicture mockPic = createMockPicture();
		List<ProductPicture> mockList = new ArrayList<>();
		mockList.add(mockPic);
		
		when(productDao.getProduct(1))
			.thenReturn(null);
		when(pictureDao.getPicturesByProductId(1))
			.thenReturn(mockList);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			pictureService.getPicturesByProductId(1);
		});
	}
	
	@Test
	public void getPicture_success() throws IOException {
		ProductPicture mockPic = createMockPicture();
		
		when(pictureDao.getPicture(1))
			.thenReturn(mockPic);
		
		ProductPicture pic = pictureService.getPicture(1);
		
		assertThat(pic).isEqualToComparingFieldByFieldRecursively(mockPic);
	}
	
	@Test
	public void getPicture_nullPic_fail() throws IOException {
		when(pictureDao.getPicture(1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			pictureService.getPicture(1);
		});
	}
	
	@Test
	public void getThumbPictureByProductId_success() throws IOException {
		ProductPicture mockPic = createMockPicture();
		Product mockProduct = createMockProduct();
		
		when(productDao.getProduct(1))
			.thenReturn(mockProduct);
		when(pictureDao.getThumbPictureByProductId(1))
			.thenReturn(mockPic);
		
		ProductPicture pic = pictureService.getThumbPictureByProductId(1);
		assertThat(pic).isEqualToComparingFieldByFieldRecursively(mockPic);

	}
	
	@Test
	public void getThumbPictureByProductId_nullProduct_fail() throws IOException {
		ProductPicture mockPic = createMockPicture();
		
		when(productDao.getProduct(1))
			.thenReturn(null);
		when(pictureDao.getThumbPictureByProductId(1))
			.thenReturn(mockPic);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			pictureService.getThumbPictureByProductId(1);
		});
	}
	
	
	
	private ProductPicture createMockPicture() throws IOException {
		BufferedImage img = new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", baos);
		
		ProductPicture pic = new ProductPicture();
		pic.setId(1);
		pic.setProduct(createMockProduct());
		pic.setPicture(baos.toByteArray());
		pic.setThumb(true);
		return pic;
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
