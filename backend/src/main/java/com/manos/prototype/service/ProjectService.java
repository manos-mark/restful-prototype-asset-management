package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.dto.ProjectDto;

public interface ProjectService {
	
	List<ProjectDto> getProjects();
	
	ProjectDto getProject(int id);
	
	void deleteProject(int id);
	
    void saveProject(ProjectDto projectDto);
}
