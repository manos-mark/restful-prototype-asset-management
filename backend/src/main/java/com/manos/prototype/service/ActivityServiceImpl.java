package com.manos.prototype.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.dto.ActivityRequestDto;
import com.manos.prototype.entity.Action;
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
	public List<ActivityDto> getActivities() {
		Long userId = userService.getCurrentUserDto().getId();
		// get activities from db
		List<Activity> acts = activityDao.getActivities(userId);
		// resolve activities into dto's
		List<ActivityDto> actList = new ArrayList<ActivityDto>();
		for (Activity act : acts) {
			ActivityDto actDto = new ActivityDto();
			actDto.setActionId(act.getAction().getId());	
			actDto.setDate(act.getDate().toString());
			actDto.setId(act.getId());
			actList.add(actDto);
		}
		return actList;
	}

	@Override
	@Transactional
	public void addActivity(ActivityRequestDto activityRequestDto) {
		Long userId = userService.getCurrentUserDto().getId();
		
		Action action = new Action();
		action.setId(activityRequestDto.getActionId());
		User tempUser = userService.getUser(userId);
		
		Activity activity = new Activity();
		activity.setAction(action);
		activity.setDate(activityRequestDto.getDate());
		activity.setUser(tempUser);
		
		try {
			activityDao.addActivity(activity);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

}
