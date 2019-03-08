package com.manos.prototype.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dao.ProjectManagerDaoImpl;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
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

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private ProjectManagerDaoImpl projectManagerDao;
	
	@Transactional
	public PageResult<ProjectVo> getProjects(PageRequest pageRequest, ProjectSearch search) {
		List<ProjectVo> projects = projectDao.getProjects(pageRequest, search);
		int totalCount = projectDao.count(search);
		// fetch because of lazy loading
		for (ProjectVo projectVo : projects) {
			Hibernate.initialize(projectVo.getProject().getProjectManager());
			Hibernate.initialize(projectVo.getProject().getStatus());
		}
		
		return new PageResult<>(projects, totalCount, pageRequest.getPageSize());
	}
	
	@Transactional
	public Project getProject(int id) {
		Project project = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, id);
		}
		return project;
	}

	@Transactional
	public void deleteProject(int id, List<Product> products) {
		Project project  = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, id);
		}
		// should delete first all the products of this project
		for (Product product : products) {
			productDao.deleteProduct(product.getId());
		}
		projectDao.deleteProject(id);
	}

	@Transactional
	public void saveProject(ProjectRequestDto dto) {
		Project project = new Project();
		
		ProjectManager projectManager = projectManagerDao.getProjectManager(dto.getProjectManagerId());
		if (projectManager == null) {
			throw new EntityNotFoundException(ProjectManager.class, dto.getProjectManagerId());
		}
		project.setCompanyName(dto.getCompanyName());
		project.setCreatedAt(LocalDate.now());
		project.setProjectManager(projectManager);
		project.setProjectName(dto.getProjectName());
		project.setStatus(new Status(Status.NEW_ID));
		
		projectDao.saveProject(project);
	}
	
	@Transactional
	public void updateProject(ProjectRequestDto dto, int projectId) {
		
		Project project = projectDao.getProject(projectId);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, projectId);
		}
		// den xreiazomai auta ta if giati xrisimopoiw not null sto projectRequestDto
		if (dto.getCompanyName() != null) {
			project.setCompanyName(dto.getCompanyName());
		}
		
		ProjectManager projectManager = projectManagerDao.getProjectManager(dto.getProjectManagerId());
		if (projectManager != null) {
			project.setProjectManager(projectManager);
		}
		
		if (dto.getProjectName() != null) {
			project.setProjectName(dto.getProjectName());
		}
		
		if (!acceptedStatuses.contains(dto.getStatusId())) {
			throw new EntityNotFoundException(Status.class, dto.getStatusId());
		}
		project.setStatus(new Status(dto.getStatusId()));
	}

	@Transactional
	public Long getProjectsCountByStatus(int statusId) {
		return projectDao.getProjectsCountByStatus(statusId);
	}

	@Transactional
	public List<Project> getProjects() {
		return projectDao.getProjects();
	}
	
	@Transactional
	public List<ProjectManager> getProjectManagers() {
		return projectManagerDao.getProjectManagers();
	}

}
