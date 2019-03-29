package com.manos.prototype.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.PictureDaoImpl;
import com.manos.prototype.dao.ProductDaoImpl;
import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.ProductPicture;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityAlreadyExistsException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@Service
public class ProjectServiceImpl {
	
	private static final ArrayList<Integer> acceptedStatuses = new ArrayList<Integer>() {
		private static final long serialVersionUID = 1L;
		{add(1);add(2);add(3);}
	};

	@Autowired
	private GenericGateway gateway;
	
	@Autowired
	private GenericFinder finder;
	
	@Autowired
	private ProjectDaoImpl projectDao;

	@Autowired
	private ProductDaoImpl productDao;
	
	@Autowired
	private PictureDaoImpl pictureDao;
	
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
		Project project = finder.findById(Project.class, id);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, id);
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		return project;
	}

	@Transactional
	public void deleteProject(int id) {
		List<Product> products = productDao.getProductsByProjectId(id);
		if (products == null) {
			throw new EntityNotFoundException(Product.class);
		}
				
		Project project  = finder.findById(Project.class, id);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, id);
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		// should delete first all the products of this project
		for (Product product : products) {
			List<ProductPicture> pictures = pictureDao.getPicturesByProductId(product.getId());
			for (ProductPicture picture: pictures) {
				gateway.delete(picture);
			}
			gateway.delete(product);
		}
		gateway.delete(project);
	}

	@Transactional
	public void saveProject(Project project, int projectManagerId) {
		
		Project tempProject = projectDao.getProjectByName(project.getProjectName());
		if (tempProject != null) {
			throw new EntityAlreadyExistsException(Project.class, project.getProjectName());
		}
		
		ProjectManager projectManager = finder.findById(ProjectManager.class, projectManagerId);
		if (projectManager == null) {
			throw new EntityNotFoundException(ProjectManager.class, projectManagerId);
		}
		project.setProjectManager(projectManager);
		project.setStatus(new Status(Status.NEW_ID));
		project.setCreatedAt(LocalDateTime.now());
		
		gateway.save(project);
	}
	
	@Transactional
	public void updateProject(ProjectRequestDto projectDto, int projectId) {
		
		
		Project project = finder.findById(Project.class, projectId);
		if (project == null) {
			throw new EntityNotFoundException(Project.class, projectId);
		}
		Hibernate.initialize(project.getProjectManager());
		Hibernate.initialize(project.getStatus());
		
		ProjectManager projectManager = finder.findById(ProjectManager.class, projectDto.getProjectManagerId());
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
	public List<Project> getProjects() {
		return finder.findAll(Project.class);
	}
	
	@Transactional
	public List<ProjectManager> getProjectManagers() {
		return finder.findAll(ProjectManager.class);
	}
	
	@Transactional
	public List<String> getProjectNames() {
		List<String> projectNames = new ArrayList<>();
		finder.findAll(Project.class).forEach(project -> {
			projectNames.add(project.getProjectName());
		});
		return projectNames;
	}

	@Transactional
	public Long getProjectsCountByStatus(int statusId) {
		return projectDao.getProjectsCountByStatus(statusId);
	}
}
