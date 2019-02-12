package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectRequestDtoToProjectConveret implements Converter<ProjectRequestDto, Project> {

	@Override
	public Project convert(ProjectRequestDto dto) {
		Project entity = new Project();
		entity.setCompanyName(dto.getCompanyName());
		entity.setDate(dto.getDate());
		entity.setProjectManager(dto.getProjectManager());
		entity.setProjectName(dto.getProjectName());
		
		int statusId = dto.getStatusId();
		entity.setStatus(new Status(statusId));
		
		return entity;
	}
	
}
