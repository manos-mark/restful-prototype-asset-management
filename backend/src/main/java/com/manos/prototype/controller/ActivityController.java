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
import com.manos.prototype.entity.Activity;
import com.manos.prototype.service.ActivityServiceImpl;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/activities")
public class ActivityController {
	
	@Autowired
	private ActivityServiceImpl activityService;

	@Autowired
	private ConversionService conversionService;

	@GetMapping
	public List<ActivityDto> getActivities() {
		List<Activity> activities = activityService.getActivities();
		return conversionService.convertList(activities, ActivityDto.class);
	}
	
	@PostMapping
	public void addActivity(@RequestBody ActivityRequestDto activityRequestDto) {
		Activity activity = conversionService.convert(activityRequestDto, Activity.class);
		activityService.saveActivity(activity);
	}
}
