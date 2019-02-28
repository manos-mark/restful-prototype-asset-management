package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProductSearch;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProductServiceImpl {

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private ProjectDaoImpl projectDao;

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
		
		
		try {
			productDao.saveProduct(product);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}
	
	@Transactional
	public void updateProduct(ProductRequestDto dto, int productId) {
		// check if project exists
//		Product product = getProductById(productId);
//		
//		product.setDate(dto.getDate());
//		
//		int projectId = product.getProjectId();
//		Project project = projectService.getProject(projectId);
//
//		if (project == null) {
//			throw new EntityNotFoundException("Project id not found - " + projectId);
//		}
	}

	@Transactional
	public List<Product> getProductsByProjectId(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		
		List<Product> list = productDao.getProductsByProjectId(id);
		return list;
	}

	@Transactional
	public Long getProductsCountByStatus(int statusId) {
		return productDao.getProductsCountByStatus(statusId);
	}

	@Transactional
	public PageResult<Product> getProducts(PageRequest pageRequest, ProductSearch search) {
		List<Product> products = productDao.getProducts(pageRequest, search);
		Long totalCount = 0L;
		if (search.getStatusIdSearchConstraint() != null) {
			totalCount = productDao.getProductsCountByStatus(search.getStatusIdSearchConstraint());
		}
		else {
			totalCount = productDao.countAll();
		}
		return new PageResult<>(products, totalCount.intValue(), pageRequest.getPageSize());
	}

	@Transactional
	public Long getProductsCountByProjectId(int projectId) {
		return productDao.getProductsCountByProjectId(projectId);
	}
}
