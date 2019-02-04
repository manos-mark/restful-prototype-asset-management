package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.dto.ActivityRequestDto;
import com.manos.prototype.service.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;

	@GetMapping
	public List<ActivityDto> getActivities() {
		return activityService.getActivities();
	}
	
	@PostMapping
	public void addActivity(@RequestBody ActivityRequestDto activityRequestDto) {
		activityService.addActivity(activityRequestDto);
	}
}
