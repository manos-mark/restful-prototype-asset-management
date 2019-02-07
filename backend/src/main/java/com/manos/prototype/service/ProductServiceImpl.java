package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manos.prototype.dao.ProductDao;
import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public List<ProductDto> getProducts() {
		List<Product> products = productDao.getProducts();
		if (products.isEmpty()) {
			throw new EntityNotFoundException("Could not find any products");
		}
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		for (Product entity : products) {
			ProductDto dto = new ProductDto();
			dto.setDate(entity.getDate());
			dto.setDescription(entity.getDescription());
			dto.setId(entity.getId());
			dto.setProductName(entity.getProductName());
			dto.setProject(entity.getProject());
			dto.setQuantity(entity.getQuantity());
			dto.setSerialNumber(entity.getSerialNumber());
			dto.setStatus(entity.getStatus());
			productsDto.add(dto);
		}
		return productsDto;
	}

	@Override
	public ProductDto getProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + id);
		}
		ProductDto dto = new ProductDto();
		dto.setDate(product.getDate());
		dto.setDescription(product.getDescription());
		dto.setId(product.getId());
		dto.setProductName(product.getProductName());
		dto.setProject(product.getProject());
		dto.setQuantity(product.getQuantity());
		dto.setSerialNumber(product.getSerialNumber());
		dto.setStatus(product.getStatus());
		return dto;
	}

	@Override
	public void deleteProduct(int id) {
		Product product = productDao.getProduct(id);
		if (product == null) {
			throw new EntityNotFoundException("Product id not found - " + id);
		}
		productDao.deleteProduct(id);
	}

	@Override
	public void saveProduct(ProductRequestDto productDto) {
		int projectId = productDto.getProjectId();
		Project project = projectDao.getProject(projectId);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + projectId);
		}
		
		Product product = new Product();
		product.setDate(productDto.getDate());
		product.setDescription(productDto.getDescription());
		product.setProductName(productDto.getProductName());
		product.setProject(project);
		product.setQuantity(productDto.getQuantity());
		product.setSerialNumber(productDto.getSerialNumber());
		product.setStatus(new Status(productDto.getStatusId()));
		
		try {
			productDao.saveProduct(product);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	public List<ProductDto> getProductsByProjectId(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		
		List<Product> products = productDao.getProductsByProjectId(id);
		if (products.isEmpty()) {
			throw new EntityNotFoundException("Could not find any products");
		}
		
		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		for (Product entity : products) {
			ProductDto dto = new ProductDto();
			dto.setDate(entity.getDate());
			dto.setDescription(entity.getDescription());
			dto.setId(entity.getId());
			dto.setProductName(entity.getProductName());
			dto.setProject(project);
			dto.setQuantity(entity.getQuantity());
			dto.setSerialNumber(entity.getSerialNumber());
			dto.setStatus(new Status(entity.getStatus().getId()));
			productsDto.add(dto);
		}
		return productsDto;
	}

}
