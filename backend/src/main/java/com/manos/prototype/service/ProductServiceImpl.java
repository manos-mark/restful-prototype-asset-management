package com.manos.prototype.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dto.PictureTypeRequestDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityAlreadyExistsException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProductServiceImpl {
	
	@Autowired
	private GenericGateway gateway;
	
	@Autowired
	private GenericFinder finder;

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private PictureDaoImpl pictureDao;

	@Transactional
	public PageResult<ProductVo> getProducts(PageRequest pageRequest, ProductSearch search) {
		List<Product> products = productDao.getProducts(pageRequest, search);
		int totalCount = productDao.count(search);
		
		List<ProductVo> productsVo = new ArrayList<>();
		for (Product product : products) {
			int picturesCount = pictureDao.getPicturesCountByProductId(product.getId());
			
			ProductVo productVo = new ProductVo();
			productVo.setPicturesCount(picturesCount);
			productVo.setProduct(product);
			productsVo.add(productVo);
		}
		
		return new PageResult<>(productsVo, totalCount, pageRequest.getPageSize());
	}
	
	@Transactional
	public List<ProductVo> getProductsByProjectId(int id) {
		Project project = finder.findById(Project.class, id);
		if (project == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		
		List<Product> list = productDao.getProductsByProjectId(id);
		
		List<ProductVo> productsVo = new ArrayList<>();
		for (Product product : list) {
			int picturesCount = pictureDao.getPicturesCountByProductId(product.getId());
			
			ProductVo productVo = new ProductVo();
			productVo.setPicturesCount(picturesCount);
			productVo.setProduct(product);
			productsVo.add(productVo);
		}
		
		return productsVo;
	}
	
	@Transactional
	public void deleteProduct(int id) {
		Product product = finder.findById(Product.class, id);
		if (product == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		
		List<ProductPicture> pictures = pictureDao.getPicturesByProductId(id);
		for (ProductPicture picture: pictures) {
			gateway.delete(picture);
		}
		
		gateway.delete(product);
	}

	@Transactional
	public void saveProduct(Product product, List<ProductPicture> pictures, int thumbIndex, int projectId) {
		// Set the thumb picture for the product
		ProductPicture thumbPicture = pictures.get(thumbIndex);
		thumbPicture.setProduct(product);
		product.setThumbPicture(thumbPicture);
		
		// Save the product
		Product tempProduct = productDao.getProductByName(product.getProductName());
		if (tempProduct != null) {
			throw new EntityAlreadyExistsException(Product.class, product.getProductName());
		}
		
		tempProduct = productDao.getProductBySerialNumber(product.getSerialNumber());
		if (tempProduct != null) {
			throw new EntityAlreadyExistsException(Product.class, product.getSerialNumber());
		}
		
		Project project = finder.findById(Project.class, projectId);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, projectId);
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		
		product.setStatus(new Status(Status.NEW_ID));
		product.setCreatedAt(LocalDate.now());
		product.setProject(project);
		gateway.save(product);
		
		// Save the pictures
		for (ProductPicture picture : pictures) {
			picture.setProduct(product);
			gateway.save(picture);
		}
	}
	
	@Transactional
	public void updateProduct(ProductRequestDto productDto, int productId, List<ProductPicture> newPictures, 
			List<PictureTypeRequestDto> pictureTypeList) {
		
		Product existingProduct = finder.findById(Product.class, productId);
		if (existingProduct == null) {
			throw new EntityNotFoundException(Product.class, productId);
		}
		Hibernate.initialize(existingProduct.getProject());
		Hibernate.initialize(existingProduct.getStatus());
		
		Project project = finder.findById(Project.class, productDto.getProjectId());
		if (project == null) {
			throw new EntityNotFoundException(Project.class, productDto.getProjectId());
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		existingProduct.setProject(project);
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setQuantity(productDto.getQuantity());
		existingProduct.setSerialNumber(productDto.getSerialNumber());
		existingProduct.setStatus(new Status(productDto.getStatusId()));

		List<ProductPicture> existingPictures = existingProduct.getPictures();
		
		int newPicturesIndex = 0;
		for (int i = 0; i < pictureTypeList.size(); i++) {
			if (pictureTypeList.get(i).getType().equals(PictureTypeRequestDto.TYPE_NEW)) {
				ProductPicture tempPicture = newPictures.get(newPicturesIndex++);
				tempPicture.setProduct(existingProduct);
				gateway.save(tempPicture);
			}
		}
		
		existingPictures = existingProduct.getPictures();
		// Set the thumb picture for the product
		if (productDto.getThumbPictureIndex() >= 0 && existingPictures.size() > 0) {
			ProductPicture thumbPicture = existingPictures.get(productDto.getThumbPictureIndex());
			thumbPicture.setProduct(existingProduct);
			existingProduct.setThumbPicture(thumbPicture);
		}
		
		for (int i = 0; i < pictureTypeList.size(); i++) {
			if (pictureTypeList.get(i).getType().equals(PictureTypeRequestDto.TYPE_DELETED)) {
				gateway.delete(existingPictures.get(i));
			} 
		}
	}

	@Transactional
	public Long getProductsCountByStatus(int statusId) {
		return productDao.getProductsCountByStatus(statusId);
	}

	@Transactional
	public Product getProduct(int id) {
		Product product = finder.findById(Product.class, id);
		if (product == null) {
			throw new EntityNotFoundException(Product.class, id);
		}
		Hibernate.initialize(product.getProject());
		Hibernate.initialize(product.getStatus());
		return product;
	}

}
