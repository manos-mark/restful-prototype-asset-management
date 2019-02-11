package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProductRequestDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectRequestDtoToProjectConveret implements Converter<ProjectRequestDto, Project> {

	@Override
	public Project convert(ProjectRequestDto dto) {
		Project entity = new Project();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	
}
