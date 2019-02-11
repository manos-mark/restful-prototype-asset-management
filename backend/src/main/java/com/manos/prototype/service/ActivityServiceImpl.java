package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public List<Activity> getActivities() {
		
		// get current user
		Long userId = userService.getCurrentUserDetails().getId();
		if (userId == null) {
			throw new EntityNotFoundException("User id not found - " + userId);
		}
		
		// get activities from db
		return activityDao.getActivitiesByUserId(userId);
	}

	
	@Override
	@Transactional
	public void saveActivity(Activity activity) {
		// get current user id, then get user
		Long userId = userService.getCurrentUserDetails().getId();
		User tempUser = userService.getUser(userId);
		if (userId == null || tempUser == null) {
			throw new EntityNotFoundException("User not found - " + userId);
		}
		activity.setUser(tempUser);
		try {
			activityDao.saveActivity(activity);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

}
