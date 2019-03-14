package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dao.ProjectManagerDaoImpl;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
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
	private PictureDaoImpl pictureDao;
	
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
	public void deleteProject(int id) {
		List<Product> products = productDao.getProductsByProjectId(id);
				
		Project project  = projectDao.getProject(id);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, id);
		}
		// should delete first all the products of this project
		for (Product product : products) {
			List<ProductPicture> pictures = pictureDao.getPicturesByProductId(product.getId());
			for (ProductPicture picture: pictures) {
				pictureDao.deletePicture(picture.getId());
			}
			productDao.deleteProduct(product.getId());
		}
		projectDao.deleteProject(id);
	}

	@Transactional
	public void saveProject(Project project, int projectManagerId) {
		ProjectManager projectManager = projectManagerDao.getProjectManager(projectManagerId);
		if (projectManager == null) {
			throw new EntityNotFoundException(ProjectManager.class, projectManagerId);
		}
		project.setProjectManager(projectManager);
		
		projectDao.saveProject(project);
	}
	
	@Transactional
	public void updateProject(ProjectRequestDto projectDto, int projectId) {
		
		
		Project project = projectDao.getProject(projectId);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, projectId);
		}
		
		ProjectManager projectManager = projectManagerDao.getProjectManager(projectDto.getProjectManagerId());
		if (projectManager != null) {
			project.setProjectManager(projectManager);
		}
		
		if (!acceptedStatuses.contains(projectDto.getStatusId())) {
			throw new EntityNotFoundException(Status.class, projectDto.getStatusId());
		}
		project.setStatus(new Status(projectDto.getStatusId()));
		
		project.setCompanyName(projectDto.getCompanyName());
		project.setProjectName(projectDto.getProjectName());
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
