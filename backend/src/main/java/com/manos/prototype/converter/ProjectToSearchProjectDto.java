package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.SearchProjectDto;
import com.manos.prototype.entity.Project;
import com.pastelstudios.convert.Converter;

@Component
public class ProjectToSearchProjectDto implements Converter<Project, SearchProjectDto>{

	@Override
	public SearchProjectDto convert(Project from) {
		SearchProjectDto to = new SearchProjectDto();
		
		to.setCompanyName(from.getCompanyName());
		to.setId(from.getId());
		to.setProjectManagerName(from.getProjectManager().getName());
		to.setProjectName(from.getProjectName());
		
		return to;
	}

}
