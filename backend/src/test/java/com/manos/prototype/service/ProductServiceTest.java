package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.PictureTypeRequestDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityAlreadyExistsException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GenericFinder.class)
public class ProductServiceTest {

	@Mock
	private ProductDaoImpl productDao;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Mock
	private ProjectDaoImpl projectDao;
	
	@Mock
	private GenericFinder finder;
	
	@Mock
	private PictureDaoImpl pictureDao;
	
	@Mock
	private SessionFactory sessionFactory;
	
	@Mock
	private Session session;
	
	@Test
	public void getProducts() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		String dateCreatedField = "date";
		OrderClause clause1 = new OrderClause(dateCreatedField, orderDirection);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
		
		ProductSearch search = new ProductSearch();
		search.setStatusId(2);
		List<ProductVo> mockProductsVos = createMockProductsVos();
		List<Product> mockProducts = createMockProducts();
		
		when(productDao.getProducts(pageRequest, search))
			.thenReturn(mockProducts);
		when(productDao.count(search))
			.thenReturn(1);
		when(pictureDao.getPicturesCountByProductId(1))
			.thenReturn(0);
		
		PageResult<ProductVo> productsVos = productService.getProducts(pageRequest, search);
		
		assertThat(productsVos.getEntities().get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProductsVos.get(0));
	}
	
	@Test
	public void getProductsByProjectId_nullProject_fail() {
		when(finder.findById(Project.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.getProductsByProjectId(1);
			});
	}
	
	@Test
	public void getProductsByProjectId_success() {
		List<Product> mockProducts = createMockProducts();
		Project mockProject = createMockProject();
		
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(productDao.getProductsByProjectId(1))
			.thenReturn(mockProducts);
		when(pictureDao.getPicturesCountByProductId(1))
			.thenReturn(3);

		List<ProductVo> products = productService.getProductsByProjectId(1);
		
		assertThat(products.get(0).getProduct())
			.isEqualToComparingFieldByFieldRecursively(mockProducts.get(0));
	}
	
	@Test
	public void deleteProduct_nullProduct_fail() {
		when(finder.findById(Product.class, 1))
			.thenReturn(null);
		when(pictureDao.getPicturesByProductId(1))
			.thenReturn(new ArrayList<>());
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.deleteProduct(1);
			});
	}
	
	@Test
	public void deleteProduct_success() {
		Product mockProduct = createMockProduct();
		List<ProductPicture> mockPictures = createMockPictures();
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		when(pictureDao.getPicturesByProductId(1))
			.thenReturn(mockPictures);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
		
		assertThatCode(() -> {
			productService.deleteProduct(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProduct_productAlreadyExists_fail() {
		Product mockProduct = createMockProduct();
		List<ProductPicture> mockPictures = createMockPictures();
		
		when(productDao.getProductByName("test"))
			.thenReturn(mockProduct);
		
		assertThatExceptionOfType(EntityAlreadyExistsException.class)
			.isThrownBy(() -> {
				productService.saveProduct(mockProduct, mockPictures, 0, 0);
			});
	}
	
	@Test
	public void saveProduct_serialNumberAlreadyExists_fail() {
		Product mockProduct = createMockProduct();
		mockProduct.setProductName("sadas");
		List<ProductPicture> mockPictures = createMockPictures();
		
		when(productDao.getProductByName("test"))
			.thenReturn(null);
		when(productDao.getProductBySerialNumber("test"))
			.thenReturn(mockProduct);
		
		assertThatExceptionOfType(EntityAlreadyExistsException.class)
			.isThrownBy(() -> {
				productService.saveProduct(mockProduct, mockPictures, 0, 0);
			});
	}
	
	@Test
	public void saveProduct_nullProject_fail() {
		Product mockProduct = createMockProduct();
		List<ProductPicture> mockPictures = createMockPictures();
		
		when(productDao.getProductByName("test"))
			.thenReturn(null);
		when(productDao.getProductBySerialNumber("test"))
			.thenReturn(null);
			
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.saveProduct(mockProduct, mockPictures, 0, 0);
			});
	}
	
	@Test
	public void saveProduct_success() {
		Product mockProduct = createMockProduct();
		Project mockProject = createMockProject();
		List<ProductPicture> mockPictures = createMockPictures();
		
		when(productDao.getProductByName("test"))
			.thenReturn(null);
		when(productDao.getProductBySerialNumber("test"))
			.thenReturn(null);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
		
		assertThatCode(() -> { 
			productService.saveProduct(mockProduct, mockPictures, 0, 1);
		}).doesNotThrowAnyException();
		
		Project project = finder.findById(Project.class, 1);
		assertThat(project).isEqualTo(mockProject);
		assertThat(project)
			.isEqualToComparingFieldByFieldRecursively(mockProject);
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
	}
	
	@Test
	public void updateProduct_nullProduct_fail() {
		when(finder.findById(Product.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.updateProduct(null, 1, null, null);
			});
	}
	
	@Test
	public void updateProduct_nullProject_fail() {
		Product mockProduct = createMockProduct();
		ProductRequestDto dto = createMockProductRequestDto();
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.updateProduct(dto, 1, new ArrayList<>(), new ArrayList<>());
			});
	}
	
	@Test
	public void updateProduct_newPicturesEmpty_success() {
		Product mockProduct = createMockProduct();
		mockProduct.setPictures(createMockPictures());
		mockProduct.setThumbPicture(mockProduct.getPictures().get(0));
		Project mockProject = createMockProject();
		ProductRequestDto dto = createMockProductRequestDto();
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
			
		assertThatCode(() -> {
			productService.updateProduct(dto, 1, new ArrayList<>(), new ArrayList<>());
		}).doesNotThrowAnyException();
		
		Product product = finder.findById(Product.class, 1);
		assertThat(product).isEqualTo(mockProduct);
		assertThat(product)
			.isEqualToComparingFieldByFieldRecursively(mockProduct);
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(product.getPictures().size()).isEqualTo(1);
	}
	
	@Test
	public void updateProduct_newPicturesEmpty_changeThumb_success() {
		Product mockProduct = createMockProduct();
		List<ProductPicture> pictures = createMockPictures();
		pictures.add(createMockPicture());
		mockProduct.setPictures(pictures);
		mockProduct.setThumbPicture(mockProduct.getPictures().get(0));
		Project mockProject = createMockProject();
		ProductRequestDto dto = createMockProductRequestDto();
		dto.setThumbPictureIndex(1);
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
			
		assertThatCode(() -> {
			productService.updateProduct(dto, 1, new ArrayList<>(), new ArrayList<>());
		}).doesNotThrowAnyException();
		
		Product product = finder.findById(Product.class, 1);
		assertThat(product).isEqualTo(mockProduct);
		assertThat(product)
			.isEqualToComparingFieldByFieldRecursively(mockProduct);
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(product.getPictures().size()).isEqualTo(2);
		assertThat(product.getThumbPicture()).isEqualTo(mockProduct.getPictures().get(1));
	}
	
	@Test
	public void updateProduct_withNewPicture_success() {
		Product mockProduct = createMockProduct();
		mockProduct.setPictures(createMockPictures());
		mockProduct.setThumbPicture(mockProduct.getPictures().get(0));
		Project mockProject = createMockProject();
		ProductRequestDto dto = createMockProductRequestDto();
		
		List<ProductPicture> newPictures = createMockPictures();
		newPictures.get(0).setId(0);
		PictureTypeRequestDto type = new PictureTypeRequestDto();
//		type.setPictureId(1);
		type.setType(PictureTypeRequestDto.TYPE_NEW);
		List<PictureTypeRequestDto> listType = new ArrayList<>();
		listType.add(type);
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
			
		assertThatCode(() -> {
			productService.updateProduct(dto, 1, newPictures, listType);
		}).doesNotThrowAnyException();
		
		Product product = finder.findById(Product.class, 1);
		assertThat(product).isEqualTo(mockProduct);
		assertThat(product)
			.isEqualToComparingFieldByFieldRecursively(mockProduct);
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
//		assertThat(product.getPictures().size()).isEqualTo(2);
//		assertThat(product.getThumbPicture()).isEqualTo(mockProduct.getPictures().get(1));
	}
	
	@Test
	public void updateProduct_withDeletedPicture_success() {
		Product mockProduct = createMockProduct();
		mockProduct.setPictures(createMockPictures());
		mockProduct.setThumbPicture(mockProduct.getPictures().get(0));
		Project mockProject = createMockProject();
		ProductRequestDto dto = createMockProductRequestDto();
		
		PictureTypeRequestDto type = new PictureTypeRequestDto();
		type.setPictureId(1);
		type.setType(PictureTypeRequestDto.TYPE_DELETED);
		List<PictureTypeRequestDto> listType = new ArrayList<>();
		listType.add(type);
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		when(sessionFactory.getCurrentSession())
			.thenReturn(session);
			
		assertThatCode(() -> {
			productService.updateProduct(dto, 1, new ArrayList<>(), listType);
		}).doesNotThrowAnyException();
		
		Product product = finder.findById(Product.class, 1);
		assertThat(product).isEqualTo(mockProduct);
		assertThat(product)
			.isEqualToComparingFieldByFieldRecursively(mockProduct);
		assertThat(mockProduct).isNotNull();
		assertThat(mockProduct).hasNoNullFieldsOrProperties();
		assertThat(product.getPictures().size()).isEqualTo(1);
	}
	
	@Test 
	public void getProductsCountByStatus() {
		assertThatCode(() -> {
			productService.getProductsCountByStatus(2);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getProduct_nullProduct_fail() {
		when(finder.findById(Product.class, null))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				productService.getProduct(1);
			});
	}
	
	@Test
	public void getProduct_success() {
		Product mockProduct = createMockProduct();
		
		when(finder.findById(Product.class, 1))
			.thenReturn(mockProduct);
		
		assertThatCode(() -> {
			productService.getProduct(1);
		}).doesNotThrowAnyException();
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
		mockProject.setCreatedAt(LocalDate.now());
		mockProject.setId(1);
		mockProject.setProjectManager(createMockManager());
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public Product createMockProduct() {
		Product mockProduct = new Product();
		mockProduct.setCreatedAt(LocalDate.now());
		mockProduct.setDescription("test");
		mockProduct.setId(1);
		mockProduct.setProductName("test");
		mockProduct.setProject(createMockProject());
		mockProduct.setQuantity(12);
		mockProduct.setSerialNumber("test");
		mockProduct.setStatus(createMockStatus());
		return mockProduct;
	}
	
	public ProductRequestDto createMockProductRequestDto() {
		ProductRequestDto dto = new ProductRequestDto();
		dto.setDescription("test");
		dto.setProductName("test");
		dto.setProjectId(1);
		dto.setQuantity(12);
		dto.setSerialNumber("123");
		dto.setStatusId(Status.NEW_ID);
		dto.setThumbPictureIndex(0);
		return dto;
	}
	
	public List<Product> createMockProducts() {
		List<Product> mockProducts = new ArrayList<Product>();
		mockProducts.add(createMockProduct());
		return mockProducts;
	}
	
	public List<ProductVo> createMockProductsVos() {
		ProductVo productVo = new ProductVo();
		productVo.setProduct(createMockProduct());
		List<ProductVo> productsVos = new ArrayList<>();
		productsVos.add(productVo);
		return productsVos;
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
	
	public List<ProductPicture> createMockPictures() {
		List<ProductPicture> list = new ArrayList<>();
		list.add(createMockPicture());
		return list;
	}
}





















