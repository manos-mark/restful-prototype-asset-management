package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.dto.ProjectDto;
import com.manos.prototype.dto.ProjectRequestDto;

public interface ProjectService {
	
	List<ProjectDto> getProjects();
	
	ProjectDto getProject(int id);
	
	void deleteProject(int id);
	
    void saveProject(ProjectRequestDto projectDto);
}
