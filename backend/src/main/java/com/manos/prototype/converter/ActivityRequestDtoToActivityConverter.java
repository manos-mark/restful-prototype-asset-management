package com.manos.prototype.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
		
		// convert datetime
		String tempDate = from.getDate();
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("MM/dd/yyyy, HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(tempDate, formatter);
		
		Activity activity = new Activity();
		activity.setAction(action);
		activity.setDate(date.toString());
		
		return activity;
	}
}
