package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public List<ProjectDto> getProjects() {
		List<Project> projects = projectDao.getProjects();
		if (projects.isEmpty()) {
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
		return projectsDto;
	}

	@Override
	public ProjectDto getProject(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		ProjectDto dto = new ProjectDto();
		dto.setId(project.getId());
		dto.setProjectName(project.getProjectName());
		dto.setCompanyName(project.getCompanyName());
		dto.setProjectManager(project.getProjectManager());
		dto.setDate(project.getDate());
		dto.setStatus(project.getStatus());
		return dto;
	}

	@Override
	public void deleteProject(int id) {
		Project project  = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		projectDao.deleteProject(id);
	}

	@Override
	public void saveProject(ProjectRequestDto projectDto) {
		Project project = new Project();
		project.setCompanyName(projectDto.getCompanyName());
		project.setDate(projectDto.getDate());
		project.setProjectManager(projectDto.getProjectManager());
		project.setProjectName(projectDto.getProjectName());
		project.setStatus(new Status(projectDto.getStatusId()));
		
		try {
			projectDao.saveProject(project);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

}
