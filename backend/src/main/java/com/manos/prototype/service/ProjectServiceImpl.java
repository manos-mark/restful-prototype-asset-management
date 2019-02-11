package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ProjectServiceImpl implements ProjectService{
		
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	@Transactional
	public List<Project> getProjects() {
		return projectDao.getProjects();
	}

	@Override
	@Transactional
	public Project getProject(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		return project;
	}

	@Override
	@Transactional
	public void deleteProject(int id) {
		Project project  = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		projectDao.deleteProject(id);
	}

	@Override
	@Transactional
	public void saveProject(Project project) {
		if (project == null) {
			throw new EntityNotFoundException("Project not found");
		}
		try {
			projectDao.saveProject(project);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public Long getProjectsCount() {
		return projectDao.getProjectsCount();
	}

}
