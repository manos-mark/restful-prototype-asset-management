package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Activity;

public interface ActivityDao {

	List<Activity> getActivities(long userId);
	
    void addActivity(Activity activity);
}
