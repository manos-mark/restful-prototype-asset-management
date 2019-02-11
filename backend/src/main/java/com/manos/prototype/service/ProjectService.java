package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Project;

public interface ProjectService {
	
	List<Project> getProjects();
	
	Long getProjectsCount();
	
	Project getProject(int id);
	
	void deleteProject(int id);
	
    void saveProject(Project project);
}
