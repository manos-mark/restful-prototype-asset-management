package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.ProjectDto;
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
		List<ProjectDto> projects = projectService.getProjects();
		return conversionService.convertList(projects, ProjectDto.class);
	}
}
