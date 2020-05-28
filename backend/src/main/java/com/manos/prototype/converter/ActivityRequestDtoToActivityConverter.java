package com.manos.prototype.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ActivityRequestDto;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.ActivityAction;
import com.pastelstudios.convert.Converter;

@Component
public class ActivityRequestDtoToActivityConverter implements Converter<ActivityRequestDto, Activity> {

	@Override
	public Activity convert(ActivityRequestDto from) {
		
		ActivityAction action = new ActivityAction();
		action.setId(from.getActionId());
		
		Activity activity = new Activity();
		activity.setAction(action);
		activity.setDate(LocalDateTime.now());
		
		return activity;
	}
}
