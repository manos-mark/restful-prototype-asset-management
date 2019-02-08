package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Activity;

public interface ActivityService {

	List<Activity> getActivities();

	void saveActivity(Activity activity);
}
