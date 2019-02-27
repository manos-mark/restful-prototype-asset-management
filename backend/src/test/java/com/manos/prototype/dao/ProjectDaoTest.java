package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
public class ProjectDaoTest {
		
	@Autowired 
	private ProjectDaoImpl projectDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(projectDao).isNotNull();
	}
	
	@Test
	@Transactional
	void getProjects_success() {
		List<Project> projects = projectDao.getProjects();
		assertThat(projects).size().isEqualTo(1);
		assertThat(projects).doesNotContainNull();
	}
	
	@Test
	@Transactional
	void getProjectsWithParams_dateOrder_success() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		String field = "project.date";
		OrderClause clause1 = new OrderClause(field, orderDirection);
		
//		String field = null;
//		String field = "productsCount";
//		OrderClause clause1 = new OrderClause(field, orderDirection);
//		orderClauses.add(clause3);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
//		pageRequest.getPageOffset();
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		List<ProjectVo> projects = projectDao.getProjects(pageRequest, search);
		assertThat(projects).isNotNull();
		assertThat(projects).isNotEmpty();
		assertThat(projects.get(0).getProject().getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getProjectsWithParams_productsCountOrder_success() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		
		String field = "productsCount";
		OrderClause clause1 = new OrderClause(field, orderDirection);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
//		pageRequest.getPageOffset();
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		List<ProjectVo> projects = projectDao.getProjects(pageRequest, search);
		assertThat(projects).isNotNull();
		assertThat(projects).isNotEmpty();
		assertThat(projects.get(0).getProject().getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getProjectsWithParams_nullDirection_fail() {
		OrderDirection orderDirection = null;
		String dateCreatedField = "date";
		OrderClause clause1 = new OrderClause(dateCreatedField, orderDirection);
		
//		String productsCountField = null;
//		productsCountField = "product";
//		OrderClause clause3 = new OrderClause(productsCountField, orderDirection);
//		orderClauses.add(clause3);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
//		pageRequest.getPageOffset();
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> { 
			projectDao.getProjects(pageRequest, search);
		});
	}
	
	@Test
	@Transactional
	void getProject_success() {
		List<Project> projects = projectDao.getProjects();
		Project project = projects.get(0);
		assertThat(project).isNotNull();
		assertThat(project).hasNoNullFieldsOrProperties();
		assertThat(project.getProjectName()).isEqualTo("firstProject");
		assertThat(project.getCompanyName()).isEqualTo("firstCompany");
		assertThat(project.getProjectManager().getName()).isEqualTo("project manager test");
		assertThat(project.getDate()).isEqualTo("2011-12-17");
		assertThat(project.getStatus().getId()).isEqualTo(2);
		assertThat(projects).size().isEqualTo(1);
		assertThat(projects).doesNotContainNull();
	}
	
	@Test
	@Transactional
	void getProject_fail() {
		assertThatExceptionOfType(NoResultException.class)
			.isThrownBy(() -> { 
				projectDao.getProject(100); 
			});
	}
	
	@Test
	@Transactional
	void deleteProject_success() {
		assertThatCode(() -> { 
			projectDao.deleteProject(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void deleteProject_fail() {
		assertThatExceptionOfType(NoResultException.class)
			.isThrownBy(() -> { 
				projectDao.deleteProject(1000);
			});
	}
	
	@Test
	@Transactional
	void saveProject_success() {
		Project project = new Project();
		project.setCompanyName("test");
		project.setProjectName("test");
		project.setProjectManager(new ProjectManager(1));
		project.setDate("2019-12-17 14:14:14");
		project.setStatus(new Status(1));
		
		assertThatCode(() -> {
			projectDao.saveProject(project);
		}).doesNotThrowAnyException();
		
		Project savedProject = projectDao.getProject(2);
		assertThat(savedProject).isNotNull();
		assertThat(savedProject.getCompanyName()).isEqualTo("test");
		assertThat(savedProject.getProjectManager().getId()).isEqualTo(1);
		assertThat(savedProject.getProjectName()).isEqualTo("test");
		assertThat(savedProject.getDate()).isEqualTo("2019-12-17 14:14:14");
		assertThat(savedProject.getStatus().getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void updateProject_success() {
		Project project = new Project();
		project.setCompanyName("test");
		project.setProjectName("test");
		project.setProjectManager(new ProjectManager(1));
		project.setDate("2019-12-17 14:14:14");
		project.setStatus(new Status(1));
		project.setId(1);
		
		assertThatCode(() -> {
			projectDao.updateProject(project);
		}).doesNotThrowAnyException();
		
		Project savedProject = projectDao.getProject(1);
		assertThat(savedProject).isNotNull();
		assertThat(savedProject.getCompanyName()).isEqualTo("test");
		assertThat(savedProject.getProjectManager().getId()).isEqualTo(1);
		assertThat(savedProject.getProjectName()).isEqualTo("test");
		assertThat(savedProject.getDate()).isEqualTo("2019-12-17 14:14:14");
		assertThat(savedProject.getStatus().getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void saveProject_fail() {
		assertThatExceptionOfType(IllegalArgumentException.class)
			.isThrownBy(() -> { 
				projectDao.saveProject(null); 
			});
	}
	
	@Test
	@Transactional
	void getProjectsCount() {
		assertThat(projectDao.getProjectsCountByStatus(2)).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void projectsCount() {
		assertThat(projectDao.count()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void projectsCountWithSearch() {
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		assertThat(projectDao.count(search)).isEqualTo(1);
	}
	
}











