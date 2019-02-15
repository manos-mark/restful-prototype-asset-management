package com.manos.prototype.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
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
		
		PageResultDto<ProjectDto> pageResultDto = new PageResultDto<>();
		pageResultDto.setItems(projectsDto);
		pageResultDto.setTotalCount(pageResult.getTotalCount());
		return pageResultDto;
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

	@DeleteMapping("/{id}")
	public void deleteProject(@PathVariable("id") int projectId) {
		projectService.deleteProject(projectId);
	}

	@PostMapping
	public void addProject(@RequestBody ProjectRequestDto projectDto) {
		Project project = conversionService.convert(projectDto, Project.class);
		project.setId(0);
		projectService.saveProject(project);
	}

	@PutMapping("/{id}")
	public void updateProject(@RequestBody ProjectRequestDto projectDto, @PathVariable("id") int projectId) {
		Project project = conversionService.convert(projectDto, Project.class);
		project.setId(projectId);
		projectService.saveProject(project);
	}
}
