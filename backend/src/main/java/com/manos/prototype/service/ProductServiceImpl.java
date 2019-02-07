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
			dto.setProjectId(entity.getProjectId());
			dto.setQuantity(entity.getQuantity());
			dto.setSerialNumber(entity.getSerialNumber());
			dto.setStatusId(entity.getStatusId());
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
		dto.setProjectId(product.getProjectId());
		dto.setQuantity(product.getQuantity());
		dto.setSerialNumber(product.getSerialNumber());
		dto.setStatusId(product.getStatusId());
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
		Product product = new Product();
		product.setDate(productDto.getDate());
		product.setDescription(productDto.getDescription());
		product.setProductName(productDto.getProductName());
		product.setProjectId(productDto.getProjectId());
		product.setQuantity(productDto.getQuantity());
		product.setSerialNumber(productDto.getSerialNumber());
		product.setStatusId(productDto.getStatusId());
		
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
			dto.setProjectId(entity.getProjectId());
			dto.setQuantity(entity.getQuantity());
			dto.setSerialNumber(entity.getSerialNumber());
			dto.setStatusId(entity.getStatusId());
			productsDto.add(dto);
		}
		return productsDto;
	}

}
