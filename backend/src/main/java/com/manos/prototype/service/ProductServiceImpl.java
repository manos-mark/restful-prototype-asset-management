package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manos.prototype.dao.ProductDao;
import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProjectDao projectDao;
	
//	@Override
//	public List<Product> getProducts() {
//		List<Product> products = productDao.getProducts();
//		if (products.isEmpty()) {
//			throw new EntityNotFoundException("Could not find any products");
//		}
//		return products;
//	}

//	@Override
//	public Product getProduct(int id) {
//		Product product = productDao.getProduct(id);
//		if (product == null) {
//			throw new EntityNotFoundException("Product id not found - " + id);
//		}
//		return product;
//	}

	@Override
	public void deleteProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + id);
		}
		productDao.deleteProduct(id);
	}

	@Override
	public void saveProduct(Product product) {
		// get the id from the pseudo-project and find the real project
		int projectId = product.getProject().getId();
		Project project = projectDao.getProject(projectId);
		
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + projectId);
		}
		product.setProject(project);
		
		try {
			productDao.saveProduct(product);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	public List<Product> getProductsByProjectId(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		
		return productDao.getProductsByProjectId(id);
	}

}
