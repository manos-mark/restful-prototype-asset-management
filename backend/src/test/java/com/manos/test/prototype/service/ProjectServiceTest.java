package com.manos.test.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.ProjectDao;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.ProjectServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@Mock
	private ProjectDao projectDao;
	
	@InjectMocks
	private ProjectServiceImpl projectService;
	
	@Test 
	public void getProjectsCount() {
		assertThatCode(() -> {
			projectService.getProjectsCount();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getProjects() {
		List<Project> mockProjects = createMockProjects();
		
		when(projectDao.getProjects())
			.thenReturn(mockProjects);
		
		List<Project> projects = projectService.getProjects();
		
		assertThat(projects.get(0))
			.isEqualTo(mockProjects.get(0));
		assertThat(projects.get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProjects.get(0));
	}
	
	@Test
	public void getProject() {
		Project mockProject = createMockProject();
		
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		
		Project project = projectService.getProject(1);
		
		assertThat(project)
			.isEqualTo(mockProject);
		assertThat(project)
			.isEqualToComparingFieldByFieldRecursively(mockProject);
	}
	
	@Test
	public void getProject_nullProjectFail() {
		when(projectDao.getProject(1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			projectService.getProject(1);
		});
	}
	
	@Test
	public void saveProject_success() {
		Project mockProject = createMockProject();
		
		assertThat(mockProject).isNotNull();
		assertThat(mockProject).hasNoNullFieldsOrProperties();
		assertThatCode(() -> {
			projectService.saveProject(mockProject);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProject_nullProjectFail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(null);
			});
	}
	
	
	
	
	
	
	public Status createMockStatus() {
		Status mockStatus = new Status();
		mockStatus.setId(2);
		mockStatus.setStatus("NEW");
		return mockStatus;
	}
	
	public Project createMockProject() {
		Project mockProject = new Project();
		mockProject.setCompanyName("test");
		mockProject.setDate("2011-12-17 13:17:17");
		mockProject.setId(1);
		mockProject.setProjectManager("test");
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public List<Project> createMockProjects() {
		List<Project> projects = new ArrayList<Project>();
		projects.add(createMockProject());
		return projects;
	}
}
