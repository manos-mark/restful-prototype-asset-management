package com.manos.prototype.finder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.Project;
import com.manos.prototype.finder.ProjectFinder;
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
public class ProjectFinderTest {
		
	@Autowired 
	private ProjectFinder projectFinder;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(projectFinder).isNotNull();
	}
	
	@Test
	@Transactional
	void getProjects_success() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		OrderClause clause1 = new OrderClause(null, orderDirection);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		List<ProjectVo> projects = projectFinder.getProjects(pageRequest, search);
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
		
		List<ProjectVo> projects = projectFinder.getProjects(pageRequest, search);
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
			projectFinder.getProjects(pageRequest, search);
		});
	}
	
	@Test
	@Transactional
	void getProjectsCount() {
		assertThat(projectFinder.getProjectsCountByStatus(2)).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void projectsCount() {
//		assertThat(projectDao.count()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void projectsCountWithSearch() {
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		
		assertThat(projectFinder.count(search)).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getProjectByName() {
		Project project = projectFinder.getProjectByName("firstProject");
		assertThat(project).isNotNull();
		assertThat(project).hasNoNullFieldsOrProperties();
	}
	
	@Test
	@Transactional
	void search() {
		List<Project> projects = projectFinder.search("firstProject", "projectName");
		assertThat(projects).isNotNull();
	}
}











