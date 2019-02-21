package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProjectSearch;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProjectServiceImpl {
	
	private static final ArrayList<Integer> acceptedStatuses = new ArrayList<Integer>() {
		private static final long serialVersionUID = 1L;
		{add(1);add(2);add(3);}
	};
	
	@Autowired
	private ProjectDaoImpl projectDao;
	
	@Transactional
	public PageResult<Project> getProjects(PageRequest pageRequest, ProjectSearch search) {
		List<Project> projects = projectDao.getProjects(pageRequest, search);
		Long totalCount = 0L;
		if (acceptedStatuses.contains(search.getStatusId())) {
			totalCount = projectDao.getProjectsCountByStatus(search.getStatusId());
		}
		else {
			totalCount = projectDao.count();
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
	public void saveProject(ProjectRequestDto dto) {
		Project project = new Project();
		
		if (dto == null) {
			throw new EntityNotFoundException("Save Project: cannot be null.");
		}
		
		if (dto.getCompanyName() == null) {
			throw new EntityNotFoundException("Save Project: - companyName cannot be null.");
		}
		project.setCompanyName(dto.getCompanyName());
		
		if (dto.getDate() == null) {
			throw new EntityNotFoundException("Save Project: - date cannot be null.");
		}
		project.setDate(dto.getDate());
		
		if (dto.getProjectManager() == null) {
			throw new EntityNotFoundException("Save Project: - projectManager cannot be null.");
		}
		project.setProjectManager(dto.getProjectManager());
		
		if (dto.getProjectName() == null) {
			throw new EntityNotFoundException("Save Project: - projectName cannot be null.");
		}
		project.setProjectName(dto.getProjectName());
		
		if (acceptedStatuses.contains(dto.getStatusId())) {
			throw new EntityNotFoundException("Save Project: - statusId must be 1, 2 or 3.");
		}
		project.setStatus(new Status(dto.getStatusId()));
		
		projectDao.saveProject(project);			
	}
	
	@Transactional
	public void updateProject(ProjectRequestDto dto, int projectId) {
		
		Project project = projectDao.getProject(projectId);
		if (project == null) {
			throw new EntityNotFoundException("Update Project: cannot find project with id - " + projectId);
		}
		
		if (dto.getCompanyName() != null) {
			project.setCompanyName(dto.getCompanyName());
		}
		
		if (dto.getDate() != null) {
			project.setDate(dto.getDate());
		}
		
		if (dto.getProjectManager() != null) {
			project.setProjectManager(dto.getProjectManager());
		}
		
		if (dto.getProjectName() != null) {
			project.setProjectName(dto.getProjectName());
		}
		
		if (acceptedStatuses.contains(dto.getStatusId())) {
			project.setStatus(new Status(dto.getStatusId()));
		} else {
			throw new EntityNotFoundException("Save Project: - statusId must be 1, 2 or 3.");
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
