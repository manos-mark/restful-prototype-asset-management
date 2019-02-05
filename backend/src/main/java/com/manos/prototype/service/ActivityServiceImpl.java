package com.manos.prototype.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
		
		// get current user
		Long userId = userService.getCurrentUserDto().getId();
		if (userId == null) {
			throw new EntityNotFoundException("User id not found - " + userId);
		}
		
		// get activities from db
		List<Activity> acts = activityDao.getActivities(userId);
		if (acts == null) {
			throw new EntityNotFoundException("Activities not found");
		}
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
		// get current user id, then get user
		Long userId = userService.getCurrentUserDto().getId();
		User tempUser = userService.getUser(userId);
		if (userId == null || tempUser == null) {
			throw new EntityNotFoundException("User not found - " + userId);
		}
		
		Action action = new Action();
		action.setId(activityRequestDto.getActionId());
		
		// convert datetime
		String tempDate = activityRequestDto.getDate();
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("MM/dd/yyyy, HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(tempDate, formatter);
		
		Activity activity = new Activity();
		activity.setAction(action);
		activity.setDate(date.toString());
		activity.setUser(tempUser);
		
		try {
			activityDao.addActivity(activity);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

}
