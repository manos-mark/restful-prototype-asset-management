package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.SearchDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.ConversionService;

@Service
public class SearchService {
	
	@Autowired
	private ProjectDaoImpl projectDao;

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private ConversionService conversionService;

	@Transactional
	public SearchDto search(String text) {
		List<Project> projects = projectDao.search(text);
		List<ProjectDto> projectDtos = conversionService.convertList(projects, ProjectDto.class);
		
		List<Product> products = productDao.search(text);
		List<ProductDto> productDtos = conversionService.convertList(products, ProductDto.class);
		
		SearchDto searchDto = new SearchDto();
		searchDto.setProducts(productDtos);
		searchDto.setProjects(projectDtos);
		return searchDto;
	}
}
