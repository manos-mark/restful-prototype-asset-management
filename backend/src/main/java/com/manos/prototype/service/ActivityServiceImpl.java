package com.manos.prototype.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ActivityDaoImpl;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;

@Service
public class ActivityServiceImpl {
	
	@Autowired
	private ActivityDaoImpl activityDao;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Transactional
	public List<Activity> getActivities() {
		
		// get current user
		Long userId = userService.getCurrentUserDetails().getId();
		
		// get activities from db
		return activityDao.getActivitiesByUserId(userId);
	}

	
	@Transactional
	public void saveActivity(Activity activity) {
		// get current user id, then get user
		Long userId = userService.getCurrentUserDetails().getId();
		User tempUser = userService.getUser(userId);
		if (userId == null || tempUser == null) {
			throw new EntityNotFoundException(User.class, userId);
		}
		activity.setUser(tempUser);
		sessionFactory.getCurrentSession().save(activity);
	}

}
