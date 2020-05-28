package com.manos.prototype.testservice;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.entity.Project;
import com.pastelstudios.db.GenericFinder;

@Service
public class ProjectTestService {

	@Autowired
	private GenericFinder finder;
	
	@Transactional(readOnly = true)
	public Project getLastProject() {
		List<Project> projects = finder.findAll(Project.class);
		return projects.get(projects.size() - 1);
	}

	@Transactional(readOnly = true)
	public Project getProject(int id) {
		return finder.findById(Project.class, id);
	}
	
}
