package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.entity.Status;
import com.manos.prototype.exception.EntityAlreadyExistsException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.finder.PictureFinder;
import com.manos.prototype.finder.ProductFinder;
import com.manos.prototype.finder.ProjectFinder;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;
import com.pastelstudios.paging.OrderClause;
import com.pastelstudios.paging.OrderDirection;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GenericFinder.class, GenericGateway.class})
public class ProjectServiceTest {

	@Mock
	private ProjectFinder projectDao;
	
	@InjectMocks
	private ProjectServiceImpl projectService;
	
	@Mock
	private ProductFinder productDao;
	
	@Mock
	private GenericFinder finder;
	
	@Mock
	private GenericGateway gateway;
	
	@Mock
	private PictureFinder pictureDao;
	
	@Test
	public void getProjects_withStatus_success() {
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
		List<ProjectVo> mockProjectsVos = createMockProjectsVos();
		
		when(projectDao.getProjects(pageRequest, search))
			.thenReturn(mockProjectsVos);
		
		PageResult<ProjectVo> projects = projectService.getProjects(pageRequest, search);
		
		assertThat(projects.getEntities().get(0))
			.isEqualTo(mockProjectsVos.get(0));
		assertThat(projects.getEntities().get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProjectsVos.get(0));
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
		List<ProjectVo> mockProjectsVos = createMockProjectsVos();
		
		when(projectDao.getProjects(pageRequest, search))
			.thenReturn(mockProjectsVos);
		
		PageResult<ProjectVo> projectsVos = projectService.getProjects(pageRequest, search);
		
		assertThat(projectsVos.getEntities().get(0))
			.isEqualTo(mockProjectsVos.get(0));
		assertThat(projectsVos.getEntities().get(0))
			.isEqualToComparingFieldByFieldRecursively(mockProjectsVos.get(0));
	}
	
	@Test
	public void getProject_fail() {
		when(finder.findById(Project.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			projectService.getProject(1);
		});
	}
	
	
	@Test
	public void getProject_success() {
		Project mockProject = createMockProject();
		
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		
		Project project = projectService.getProject(1);
		
		assertThat(project)
			.isEqualTo(mockProject);
		assertThat(project)
			.isEqualToComparingFieldByFieldRecursively(mockProject);
	}
	
	@Test
	public void deleteProject_nullProduct_fail() {
		when(productDao.getProductsByProjectId(2))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.deleteProject(2);
			});
	}
	
	@Test
	public void deleteProject_nullProject_fail() {
		Product mockProduct = createMockProduct();
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(mockProduct);

		when(productDao.getProductsByProjectId(2))
			.thenReturn(mockProducts);
		when(finder.findById(Project.class, 2))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.deleteProject(2);
			});
	}
	
	@Test
	public void deleteProject_success() {
		Product mockProduct = createMockProduct();
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(mockProduct);
		Project mockProject = createMockProject();
		
		when(productDao.getProductsByProjectId(2))
			.thenReturn(mockProducts);
		when(pictureDao.getPicturesByProductId(1))
			.thenReturn(new ArrayList<>());
		when(finder.findById(Project.class, 2))
			.thenReturn(mockProject);
		
		assertThatCode(() -> {
			projectService.deleteProject(2);
			
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void addProject_success() {
		ProjectManager mockProjectManager = createMockProjectManager();
		Project mockProject = createMockProject();
		
		when(projectDao.getProjectByName("name"))
			.thenReturn(mockProject);
		when(finder.findById(ProjectManager.class, 1))
			.thenReturn(mockProjectManager);
		
		assertThatCode(() -> {
			projectService.saveProject(mockProject, 1);
			
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveProject_projectAlreadyExists_Fail() {
		Project mockProject = createMockProject();
		
		when(projectDao.getProjectByName("test"))
			.thenReturn(mockProject);
		
		assertThatExceptionOfType(EntityAlreadyExistsException.class)
			.isThrownBy(() -> {
				projectService.saveProject(mockProject, 1);
			});
	}
	
	@Test
	public void saveProject_nullProjectManager_fail() {
		Project mockProject = createMockProject();
		
		when(projectDao.getProjectByName("name"))
			.thenReturn(mockProject);
		when(finder.findById(ProjectManager.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.saveProject(mockProject, 1);
			});
	}
	

	@Test
	public void updateProject_success() {
		Project mockProject = createMockProject();
		ProjectRequestDto mockDto = createMockProjectRequesDto();
		ProjectManager projectManager = createMockProjectManager();
		
		when(finder.findById(ProjectManager.class, 1))
			.thenReturn(projectManager);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		
		assertThatCode(() -> {
			projectService.updateProject(mockDto, 1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateProject_nullProjectFail() {
		ProjectRequestDto mockDto = createMockProjectRequesDto();

		when(finder.findById(Project.class, 1))
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
		
		when(finder.findById(ProjectManager.class, 1))
			.thenReturn(projectManager);
		when(finder.findById(Project.class, 1))
			.thenReturn(mockProject);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				projectService.updateProject(mockDto, 1);
			});
	}
	
	@Test
	public void getProjects() {
		List<Project> mockProjects = createMockProjects();
		
		when(finder.findAll(Project.class))
		.thenReturn(mockProjects);
		
		assertThatCode(() -> {
			projectService.getProjects();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getProjectManagers() {
		List<Project> mockProjects = createMockProjects();
		
		when(finder.findAll(Project.class))
		.thenReturn(mockProjects);
		
		assertThatCode(() -> {
			projectService.getProjectManagers();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getProjectNames() {
		List<Project> mockProjects = createMockProjects();
		
		when(finder.findAll(Project.class))
			.thenReturn(mockProjects);
		
		assertThatCode(() -> {
			projectService.getProjectNames();
		}).doesNotThrowAnyException();
	}
	
	@Test 
	public void getProjectsCount() {
		assertThatCode(() -> {
			projectService.getProjectsCountByStatus(2);
		}).doesNotThrowAnyException();
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
		mockProject.setCreatedAt(LocalDateTime.now());
		mockProject.setId(1);
		mockProject.setProjectManager(mockPrManager);
		mockProject.setProjectName("test");
		mockProject.setStatus(createMockStatus());
		return mockProject;
	}
	
	public ProjectRequestDto createMockProjectRequesDto() {
		ProjectRequestDto mockProject = new ProjectRequestDto();
		mockProject.setCompanyName("test");
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
	
	public List<ProjectVo> createMockProjectsVos() {
		ProjectVo projectVo = new ProjectVo();
		projectVo.setProject(createMockProject());
		List<ProjectVo> projectsVos = new ArrayList<>();
		projectsVos.add(projectVo);
		return projectsVos;
	}
	
	public Product createMockProduct() {
		Product mockProduct = new Product();
		mockProduct.setCreatedAt(LocalDateTime.now());
		mockProduct.setDescription("test");
		mockProduct.setId(1);
		mockProduct.setProductName("test");
		mockProduct.setProject(createMockProject());
		mockProduct.setQuantity(12);
		mockProduct.setSerialNumber("test");
		mockProduct.setStatus(createMockStatus());
		return mockProduct;
	}
}
