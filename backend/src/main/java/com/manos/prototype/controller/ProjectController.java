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

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectRequestDto;
import com.manos.prototype.entity.Project;
import com.manos.prototype.service.ProjectService;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ConversionService conversionService;
	
	@GetMapping
	public List<ProjectDto> getProjects() {
		List<Project> projects = projectService.getProjects();
		return conversionService.convertList(projects, ProjectDto.class);
	}
	
	@GetMapping("/count")
	public Long getProjectsCount() {
		return projectService.getProjectsCount();
	}
	
	@GetMapping("/{projectId}")
	public ProjectDto getProject(@PathVariable int projectId) {
		Project project = projectService.getProject(projectId);
		return conversionService.convert(project, ProjectDto.class);
	}
	
	@DeleteMapping("/{projectId}")
	public void deleteProject(@PathVariable int projectId) {
		projectService.deleteProject(projectId);
	}
	
	@PostMapping
	public void addProject(@RequestBody ProjectRequestDto projectDto) {
		Project project = conversionService.convert(projectDto, Project.class);
		project.setId(0);
		projectService.saveProject(project);
	}
	
	@PutMapping
	public void updateProject(@RequestBody ProjectRequestDto projectDto) {
		Project project = conversionService.convert(projectDto, Project.class);
		projectService.saveProject(project);
	}
}






