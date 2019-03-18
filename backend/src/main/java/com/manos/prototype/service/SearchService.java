package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.SearchDto;
import com.manos.prototype.dto.SearchProductDto;
import com.manos.prototype.dto.SearchProjectDto;
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
		String[] projectFields = {"projectName", "companyName", "projectManager.name"};
		List<SearchProjectDto> projectDtos = new ArrayList<>();
		for (String field : projectFields) {
			List<Project> projects = new ArrayList<>();
			projects.addAll(projectDao.search(text, field));
			projects.forEach(from -> {
				SearchProjectDto to = conversionService.convert(from, SearchProjectDto.class);
				to.setField(field);
				projectDtos.add(to);
			});
		}
		
		String[] productFields = {"productName", "serialNumber"};
		List<SearchProductDto> productDtos = new ArrayList<>();
		for (String field : productFields) {
			List<Product> products = new ArrayList<>();
			products.addAll(productDao.search(text, field));
			products.forEach(from -> {
				SearchProductDto to = conversionService.convert(from, SearchProductDto.class);
				to.setField(field);
				productDtos.add(to);
			});
		}
		
		SearchDto searchDto = new SearchDto();
		searchDto.setProducts(productDtos);
		searchDto.setProjects(projectDtos);
		return searchDto;
	}
}
