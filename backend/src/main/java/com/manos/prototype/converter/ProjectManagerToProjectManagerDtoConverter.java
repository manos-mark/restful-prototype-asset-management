package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectManagerDto;
import com.manos.prototype.entity.ProjectManager;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectManagerToProjectManagerDtoConverter implements Converter<ProjectManager, ProjectManagerDto> {

	@Override
	public ProjectManagerDto convert(ProjectManager from) {
		
		ProjectManagerDto dto = new ProjectManagerDto();
		
		dto.setId(from.getId());
		dto.setName(from.getName());
		
		return dto;
	}

}
