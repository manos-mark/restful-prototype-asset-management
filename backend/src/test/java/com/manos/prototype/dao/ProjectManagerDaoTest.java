package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.ProjectManager;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
public class ProjectManagerDaoTest {
	
	@Autowired 
	private ProjectManagerDaoImpl projectManagerDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(projectManagerDao).isNotNull();
	}
	
	@Test
	@Transactional
	void getProjectManager_success() {
		ProjectManager projectManager = projectManagerDao.getProjectManager(1);
		assertThat(projectManager.getName()).isEqualTo("project manager test");
	}
	
	@Test
	@Transactional
	void getProjectManagers_success() {
		List<ProjectManager> projectManagers = projectManagerDao.getProjectManagers();
		assertThat(projectManagers.size()).isEqualTo(1);
	}
}
