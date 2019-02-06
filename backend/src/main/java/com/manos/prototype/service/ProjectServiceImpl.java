package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public List<ProjectDto> getProjects() {
		List<Project> projects = projectDao.getProjects();
		if (projects == null) {
			throw new EntityNotFoundException("Could not find any project");
		}
		List<ProjectDto> projectsDto = new ArrayList<ProjectDto>();
		for (Project entity : projects) {
			ProjectDto dto = new ProjectDto();
			dto.setId(entity.getId());
			dto.setProjectName(entity.getProjectName());
			dto.setCompanyName(entity.getCompanyName());
			dto.setProjectManager(entity.getProjectManager());
			dto.setDate(entity.getDate());
			dto.setStatus(entity.getStatus());
			projectsDto.add(dto);
		}
		return null;
	}

	@Override
	public ProjectDto getProject(int id) {
		return null;
	}

	@Override
	public void deleteProject(int id) {
		
	}

	@Override
	public void saveProject(ProjectDto projectDto) {
		
	}

}
