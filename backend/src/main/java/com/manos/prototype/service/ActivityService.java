package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.dto.ActivityRequestDto;

public interface ActivityService {

	List<ActivityDto> getActivities();

	void addActivity(ActivityRequestDto activityDto);
}
