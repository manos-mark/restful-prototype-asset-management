package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectToProjectDtoConveret implements Converter<Project, ProjectDto> {

	@Override
	public ProjectDto convert(Project project) {
		ProjectDto dto = new ProjectDto();
		BeanUtils.copyProperties(project, dto);
		return dto;
	}
	
}
