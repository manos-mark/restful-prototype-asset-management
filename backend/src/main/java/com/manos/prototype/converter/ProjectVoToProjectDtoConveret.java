package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectManagerDto;
import com.manos.prototype.vo.ProjectVo;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectVoToProjectDtoConveret implements Converter<ProjectVo, ProjectDto> {

	@Override
	public ProjectDto convert(ProjectVo entity) {
		
		ProjectDto dto = new ProjectDto();
		
		dto.setCompanyName(entity.getProject().getCompanyName());
		dto.setCreatedAt(entity.getProject().getCreatedAt());
		dto.setId(entity.getProject().getId());
		dto.setProjectName(entity.getProject().getProjectName());
		dto.setStatusId(entity.getProject().getStatus().getId());
		dto.setProductsCount(entity.getProductsCount());
		
		ProjectManagerDto projectManagerDto = new ProjectManagerDto();
		projectManagerDto.setId(entity.getProject().getProjectManager().getId());
		projectManagerDto.setName(entity.getProject().getProjectManager().getName());
		
		dto.setProjectManager(projectManagerDto);
		
		return dto;
	}
	
}
