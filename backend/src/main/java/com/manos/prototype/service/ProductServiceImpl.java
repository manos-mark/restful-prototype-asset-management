package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;

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
	public void saveProduct(Product product) {
		// if id is not 0 then update
		if (product.getId() != 0) {
			try {
				productDao.updateProduct(product);			
			} catch (Exception e) {
				throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
			}
		} 
		// if id is 0 then add new
		else {
			try {
				productDao.saveProduct(product);			
			} catch (Exception e) {
				throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
			}
		}
	}

	@Transactional
	public List<Product> getProductsByProjectId(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		
		return productDao.getProductsByProjectId(id);
	}

	@Transactional
	public Long getProductsCountByStatus(int statusId) {
		return productDao.getProductsCountByStatus(statusId);
	}
}
