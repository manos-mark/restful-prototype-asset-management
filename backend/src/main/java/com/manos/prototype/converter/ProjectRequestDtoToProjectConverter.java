package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectRequestDtoToProjectConverter implements Converter<ProjectRequestDto, Project>{

	@Override
	public Project convert(ProjectRequestDto dto) {
		Project project = new Project();
		
		project.setCompanyName(dto.getCompanyName());
		project.setProjectName(dto.getProjectName());
		
		return project;
	}
}
