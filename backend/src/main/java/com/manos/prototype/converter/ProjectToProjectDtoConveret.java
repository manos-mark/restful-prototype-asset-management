package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectToProjectDtoConveret implements Converter<Project, Product> {

	@Override
	public Product convert(Project project) {
		ProjectDto dto = new ProjectDto();
		BeanUtils.copyProperties(source, target);
		return product;
	}
	
}
