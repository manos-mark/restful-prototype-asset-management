package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Project;

public interface ProjectDao {

	List<Project> getProjects();
	
	Project getProject(int id);
	
	void deleteProject(int id);
	
    void saveProject(Project project);

	Long getProjectsCountByStatus(int id);

	void updateProject(Project project);
}
