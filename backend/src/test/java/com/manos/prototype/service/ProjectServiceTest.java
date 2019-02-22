package com.manos.prototype.service;

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

import com.manos.prototype.dao.ProjectDaoImpl;
import com.manos.prototype.dao.ProjectManagerDaoImpl;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.search.ProjectSearch;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

	@Mock
	private ProjectDaoImpl projectDao;
	
	@InjectMocks
	private ProjectServiceImpl projectService;
	
	@Mock 
	private ProjectManagerDaoImpl projectManagerDao;
	
	@Test 
	public void getProjectsCount() {
		assertThatCode(() -> {
			projectService.getProjectsCountByStatus(2);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getProjects_noParams_success() {
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
	public void getProjects_withParams_withStatus_success() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		String dateCreatedField = "date";
		OrderClause clause1 = new OrderClause(dateCreatedField, orderDirection);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(2);
		List<Project> mockProjects = createMockProjects();
		
		when(projectDao.getProjects(pageRequest, search))
			.thenReturn(mockProjects);
		
		PageResult<Project> projects = projectService.getProjects(pageRequest, search);
		
		assertThat(projects.getEntities().get(0))
			.isEqualTo(mockProjects.get(0));
		assertThat(projects.getEntities().get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProjects.get(0));
	}
	
	@Test
	public void getProjects_withParams_noStatus_success() {
		OrderDirection orderDirection = OrderDirection.ASCENDING;
		String dateCreatedField = "date";
		OrderClause clause1 = new OrderClause(dateCreatedField, orderDirection);
		
		List<OrderClause> orderClauses = new ArrayList<>();
		orderClauses.add(clause1);
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(1);
		pageRequest.setPageSize(5);
		pageRequest.setOrderClauses(orderClauses);
		
		ProjectSearch search = new ProjectSearch();
		search.setStatusId(null);
		List<Project> mockProjects = createMockProjects();
		
		when(projectDao.getProjects(pageRequest, search))
			.thenReturn(mockProjects);
		
		PageResult<Project> projects = projectService.getProjects(pageRequest, search);
		
		assertThat(projects.getEntities().get(0))
			.isEqualTo(mockProjects.get(0));
		assertThat(projects.getEntities().get(0))
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
	public void addProject_success() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		
		assertThat(dto).isNotNull();
		assertThat(dto).hasNoNullFieldsOrProperties();
		assertThatCode(() -> {
			projectService.saveProject(dto);
			
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProject_nullProjectFail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(null);
			});
	}
	
	@Test
	public void saveProject_nullCompanyNameFail() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		dto.setCompanyName(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(dto);
			});
	}
	
	@Test
	public void saveProject_nullDateFail() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		dto.setDate(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(dto);
			});
	}
	
	@Test
	public void saveProject_nullProjectManagerFail() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		dto.setProjectManagerId(0);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(dto);
			});
	}
	
	@Test
	public void saveProject_nullProjectNameFail() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		dto.setProjectName(null);
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(dto);
			});
	}
	
	@Test
	public void saveProject_wrongStatusIdFail() {
		ProjectRequestDto dto = createMockProjectRequesDto();
		dto.setStatusId(4);
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(dto);
			});
	}
	
	@Test
	public void deleteProject_success() {
		Project mockProject = createMockProject();
		
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		
		assertThatCode(() -> {
			projectService.deleteProject(1);
			
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void deleteProject_nullProject_fail() {
		
		when(projectDao.getProject(2))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			projectService.deleteProject(2);
		});
	}
	
	@Test
	public void updateProject_success() {
		Project mockProject = createMockProject();
		ProjectRequestDto mockDto = createMockProjectRequesDto();
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		
		assertThatCode(() -> {
			projectService.updateProject(mockDto, 1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateProject_nullProjectFail() {
		ProjectRequestDto mockDto = createMockProjectRequesDto();
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		
		when(projectDao.getProject(1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.updateProject(mockDto, 1);
			});
	}
	
	@Test
	public void updateProject_wrongStatusIdFail() {
		Project mockProject = createMockProject();
		ProjectRequestDto mockDto = createMockProjectRequesDto();
		mockDto.setStatusId(4);
		ProjectManager projectManager = createMockProjectManager();
		
		when(projectManagerDao.getProjectManager(1))
			.thenReturn(projectManager);
		
		when(projectDao.getProject(1))
			.thenReturn(mockProject);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.updateProject(mockDto, 1);
			});
	}
	
	
	
	
	public Status createMockStatus() {
		Status mockStatus = new Status();
		mockStatus.setId(2);
		mockStatus.setStatus("NEW");
		return mockStatus;
	}
	
	public ProjectManager createMockProjectManager() {
		ProjectManager mockPrManager = new ProjectManager();
		mockPrManager.setId(1);
		mockPrManager.setName("project manager test");
		return mockPrManager;
	}
	
	public Project createMockProject() {
		ProjectManager mockPrManager = createMockProjectManager();
		Project mockProject = new Project();
		mockProject.setCompanyName("test");
		mockProject.setDate("17/12/2011, 13:17:17");
		mockProject.setId(1);
		mockProject.setProjectManager(mockPrManager);
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public ProjectRequestDto createMockProjectRequesDto() {
		ProjectRequestDto mockProject = new ProjectRequestDto();
		mockProject.setCompanyName("test");
		mockProject.setDate("17/12/2011, 13:17:17");
		mockProject.setProjectManagerId(1);
		mockProject.setProjectName("test");
		mockProject.setStatusId(2);
		return mockProject;
	}
	
	public List<Project> createMockProjects() {
		List<Project> projects = new ArrayList<Project>();
		projects.add(createMockProject());
		return projects;
	}
}
