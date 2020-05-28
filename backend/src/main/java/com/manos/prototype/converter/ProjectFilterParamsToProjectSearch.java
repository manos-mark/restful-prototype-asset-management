package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.ProjectFilterParams;
import com.manos.prototype.search.ProjectSearch;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectFilterParamsToProjectSearch implements Converter<ProjectFilterParams, ProjectSearch>{

	@Override
	public ProjectSearch convert(ProjectFilterParams from) {
		ProjectSearch projectSearch = new ProjectSearch();
		BeanUtils.copyProperties(from, projectSearch);
		return projectSearch;
	}
}
