package com.manos.prototype.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.manos.prototype.vo.ProductVo;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private ProjectDaoImpl projectDao;
	
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
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		
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
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + id);
		}
		productDao.deleteProduct(id);
	}

	@Transactional
	public void saveProduct(ProductRequestDto dto) {
		Product product = new Product();
		
		Project project = projectDao.getProject(dto.getProjectId());
		if (project == null) {
			throw new EntityNotFoundException("Save Product: project not found with id - " + dto.getProjectId());
		}
		product.setProject(project);
		product.setCreatedAt(LocalDate.now());
		product.setDescription(dto.getDescription());
		product.setProductName(dto.getProductName());
		product.setQuantity(dto.getQuantity());
		product.setSerialNumber(dto.getSerialNumber());
		product.setStatus(new Status(Status.NEW_ID));
//		product.setThumbPicture(thumbPicture);
		
		productDao.saveProduct(product);			
	}
	
	@Transactional
	public void updateProduct(ProductRequestDto dto, int productId) {
		Product product = productDao.getProduct(productId);
		if (product == null) {
			throw new EntityNotFoundException("Update Product: cannot find product with id - " + productId);
		}
		
		Project project = projectDao.getProject(dto.getProjectId());
		if (project == null) {
			throw new EntityNotFoundException("Save Product: project not found with id - " + dto.getProjectId());
		}
		product.setProject(project);
		product.setDescription(dto.getDescription());
		product.setProductName(dto.getProductName());
		product.setQuantity(dto.getQuantity());
		product.setSerialNumber(dto.getSerialNumber());
		product.setStatus(new Status(dto.getStatusId()));
//		product.setThumbPicture(thumbPicture);
	}

	@Transactional
	public Long getProductsCountByStatus(int statusId) {
		return productDao.getProductsCountByStatus(statusId);
	}

	@Transactional
	public Product getProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + id);
		}
		return product;
	}

//	@Transactional
//	public Long getProductsCountByProjectId(int projectId) {
//		return productDao.getProductsCountByProjectId(projectId);
//	}
}
