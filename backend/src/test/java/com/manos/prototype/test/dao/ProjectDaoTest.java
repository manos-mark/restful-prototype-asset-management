package com.manos.prototype.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
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
		project.setProjectManager("test");
		project.setDate("2019-12-17 14:14:14");
		project.setStatus( new Status(1));
		
		assertThatCode(() -> {
			projectDao.saveProject(project);
		}).doesNotThrowAnyException();
		
		Project savedProject = projectDao.getProject(2);
		assertThat(savedProject).isNotNull();
		assertThat(savedProject.getCompanyName()).isEqualTo("test");
		assertThat(savedProject.getProjectManager()).isEqualTo("test");
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
	
}











