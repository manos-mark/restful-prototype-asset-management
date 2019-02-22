package com.manos.prototype.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.controller.params.ProjectFilterParams;
import com.manos.prototype.controller.params.ProjectOrderAndPageParams;
import com.manos.prototype.dto.PageResultDto;
import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectManagerDto;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.entity.ProjectManager;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.service.ProductServiceImpl;
import com.manos.prototype.service.ProjectServiceImpl;
import com.pastelstudios.convert.ConversionService;
import com.pastelstudios.paging.PageRequest;
import com.pastelstudios.paging.PageResult;

@RestController
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ConversionService conversionService;

	@GetMapping
	public PageResultDto<ProjectDto> getProjectsPaginated(@Valid ProjectOrderAndPageParams pageParams,
			@Valid ProjectFilterParams filterParams) {
		
		PageRequest pageRequest = conversionService.convert(pageParams, PageRequest.class);
		ProjectSearch search = conversionService.convert(filterParams, ProjectSearch.class);
		
		PageResult<Project> pageResult = projectService.getProjects(pageRequest, search);
		
		List<ProjectDto> projectsDto = conversionService.convertList(pageResult.getEntities(), ProjectDto.class);
		
		for (ProjectDto project : projectsDto) {
			Long productsCount = productService.getProductsCountByProjectId(project.getId());
			project.setProductsCount(productsCount);
		}
		
		PageResultDto<ProjectDto> pageResultDto = new PageResultDto<>();
		pageResultDto.setItems(projectsDto);
		pageResultDto.setTotalCount(pageResult.getTotalCount());
		
		return pageResultDto;
	}
	
	@GetMapping("/all")
	public List<ProjectDto> getProjects() {
		List<Project> entityList = projectService.getProjects();
		List<ProjectDto> dtoList = new ArrayList<>();
		for (Project entity : entityList) {
			ProjectDto temp = new ProjectDto();
			temp.setId(entity.getId());
			temp.setProjectName(entity.getProjectName());
			dtoList.add(temp);
		}
		return dtoList;
	}
	
	@GetMapping("/project-managers")
	public List<ProjectManagerDto> getProjectManagers() {
		List<ProjectManager> entityList = projectService.getProjectManagers();
		List<ProjectManagerDto> dtoList = new ArrayList<>();
		for (ProjectManager entity : entityList) {
			ProjectManagerDto dto = new ProjectManagerDto();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@GetMapping("/{id}/products")
	public List<ProductDto> getProducts(@PathVariable("id") int projectId) {
		List<Product> products = productService.getProductsByProjectId(projectId);
		return conversionService.convertList(products, ProductDto.class);
	}
	
	@GetMapping("/{id}")
	public ProjectDto getProject(@PathVariable("id") int projectId) {
		Project project = projectService.getProject(projectId);
		return conversionService.convert(project, ProjectDto.class);
	}

	@PostMapping("/count")
	public Long getProjectsCountByStatus(@RequestBody StatusRequestDto statusId) {
		return projectService.getProjectsCountByStatus(statusId.getStatusId());
	}

//	@DeleteMapping("/{id}")
//	public void deleteProject(@PathVariable("id") int projectId) {
//		projectService.deleteProject(projectId);
//	}

	@PostMapping
	public void addProject(@RequestBody ProjectRequestDto projectDto) {
		projectService.saveProject(projectDto);
	}

	@PutMapping("/{id}")
	public void updateProject(@RequestBody ProjectRequestDto projectDto, @PathVariable("id") int projectId) {
		projectService.updateProject(projectDto, projectId);
	}
}
