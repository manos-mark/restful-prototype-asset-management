package com.manos.prototype.test.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Project;
import com.manos.prototype.test.config.AppConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
public class ProjectDaoTest {
		
	@Autowired 
	private ProjectDao projectDao;
	
	@Test
	@Transactional
	void getProjects_success() {
		List<Project> projects = projectDao.getProjects();
		assertThat(projects).size().isEqualTo(1);
		assertThat(projects).doesNotContainNull();
	}

	@Test
	@Transactional
	void getProjects_fail() {
		
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
		assertThat(project.getProjectManager()).isEqualTo("firstProjectManager");
		assertThat(project.getDate()).isEqualTo("2011-12-17");
		assertThat(project.getStatus().getId()).isEqualTo(2);
		assertThat(projects).size().isEqualTo(1);
		assertThat(projects).doesNotContainNull();
	}
	
}
