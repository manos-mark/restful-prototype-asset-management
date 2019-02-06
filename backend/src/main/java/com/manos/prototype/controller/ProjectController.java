package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.entity.Project;

@RestController
@RequestMapping("/project")
public class ProjectController {
	
//	@Autowired
//	private ProjectService projectService;
	
//	@GetMapping
//	public List<Project> getProjects() {
//		return projectService.getProjects();
//	}
}
