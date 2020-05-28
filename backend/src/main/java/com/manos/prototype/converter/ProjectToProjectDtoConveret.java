package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectManagerDto;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectToProjectDtoConveret implements Converter<Project, ProjectDto> {

	@Override
	public ProjectDto convert(Project entity) {
		
		ProjectDto dto = new ProjectDto();
		
		dto.setCompanyName(entity.getCompanyName());
		dto.setCreatedAt(entity.getCreatedAt());
		dto.setId(entity.getId());
		dto.setProjectName(entity.getProjectName());
		dto.setStatusId(entity.getStatus().getId());
		
		ProjectManagerDto projectManagerDto = new ProjectManagerDto();
		projectManagerDto.setId(entity.getProjectManager().getId());
		projectManagerDto.setName(entity.getProjectManager().getName());
				
		dto.setProjectManager(projectManagerDto);

		return dto;
	}
	
}
