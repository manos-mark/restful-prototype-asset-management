package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.entity.Project;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProjectSearch;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProjectServiceImpl {
		
	@Autowired
	private ProjectDaoImpl projectDao;
	
	@Transactional
	public PageResult<Project> getProjects(PageRequest pageRequest, ProjectSearch search) {
		List<Project> projects = projectDao.getProjects(pageRequest, search);
		Long totalCount = 0L;
		if (search.getStatusId() != null) {
			totalCount = projectDao.getProjectsCountByStatus(search.getStatusId());
		}
		else {
			totalCount = projectDao.countAll();
		}
		return new PageResult<>(projects, totalCount.intValue(), pageRequest.getPageSize());
	}
	
	@Transactional
	public Project getProject(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		return project;
	}

	@Transactional
	public void deleteProject(int id) {
		Project project  = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException("Project id not found - " + id);
		}
		projectDao.deleteProject(id);
	}

	@Transactional
	public void saveProject(Project project) {
		// if id is not 0 then update
		if (project.getId() != 0) {
			try {
				projectDao.updateProject(project);			
			} catch (Exception e) {
				throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
			}
		}
		// if id is 0 then add new
		else {
			try {
				projectDao.saveProject(project);			
			} catch (Exception e) {
				throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
			}
		}
	}

	@Transactional
	public Long getProjectsCountByStatus(int statusId) {
		return projectDao.getProjectsCountByStatus(statusId);
	}

	@Transactional
	public List<Project> getProjects() {
		return projectDao.getProjects();
	}

}
