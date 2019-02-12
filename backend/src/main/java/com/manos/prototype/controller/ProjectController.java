package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.ProductDto;
import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.dto.StatusRequestDto;
import com.manos.prototype.entity.Product;
import com.manos.prototype.entity.Project;
import com.manos.prototype.service.ProductService;
import com.manos.prototype.service.ProjectService;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ConversionService conversionService;
	
	@GetMapping
	public List<ProjectDto> getProjects() {
		List<Project> projects = projectService.getProjects();
		return conversionService.convertList(projects, ProjectDto.class);
	}
	
	@PostMapping("/count")
	public Long getProjectsCountByStatus(@RequestBody StatusRequestDto statusId) {
		return projectService.getProjectsCountByStatus(statusId.getStatusId());
	}
	
	@GetMapping("/{id}")
	public ProjectDto getProject(@PathVariable("id") int projectId) {
		Project project = projectService.getProject(projectId);
		return conversionService.convert(project, ProjectDto.class);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProject(@PathVariable("id") int projectId) {
		projectService.deleteProject(projectId);
	}
	
	@GetMapping("/{id}/products")
	public List<ProductDto> getProducts(@PathVariable("id") int projectId) {
		List<Product> products =  productService.getProductsByProjectId(projectId);
		return conversionService.convertList(products, ProductDto.class);
	}
	
	@PostMapping
	public void addProject(@RequestBody ProjectRequestDto projectDto) {
		Project project = conversionService.convert(projectDto, Project.class);
		project.setId(0);
		projectService.saveProject(project);
	}
	
	@PutMapping("/{id}")
	public void updateProject(@RequestBody ProjectRequestDto projectDto,
			@PathVariable("id") int projectId) {
		Project project = conversionService.convert(projectDto, Project.class);
		project.setId(projectId);
		projectService.saveProject(project);
	}
}






