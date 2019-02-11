package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public List<Project> getProjects() {
		return projectDao.getProjects();
	}

	@Override
	public Project getProject(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		return project;
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
	public void saveProject(Project project) {
//		Project project = new Project();
//		project.setCompanyName(projectDto.getCompanyName());
//		project.setDate(projectDto.getDate());
//		project.setProjectManager(projectDto.getProjectManager());
//		project.setProjectName(projectDto.getProjectName());
//		project.setStatus(new Status(projectDto.getStatusId()));
		
		try {
			projectDao.saveProject(project);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

}
