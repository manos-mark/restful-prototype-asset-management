package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectToProjectDtoConveret implements Converter<Project, ProjectDto> {

	@Override
	public ProjectDto convert(Project entity) {
		ProjectDto dto = new ProjectDto();
		dto.setCompanyName(entity.getCompanyName());
		dto.setDate(entity.getDate());
		dto.setId(entity.getId());
		dto.setProjectManager(entity.getProjectManager());
		dto.setProjectName(entity.getProjectName());
		dto.setStatusId(entity.getStatus().getId());
		return dto;
	}
	
}
