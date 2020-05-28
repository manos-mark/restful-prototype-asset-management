package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.entity.Activity;
import com.pastelstudios.convert.Converter;

@Component
public class ActivityToActivityDtoConverter implements Converter<Activity, ActivityDto> {

	@Override
	public ActivityDto convert(Activity from) {
		ActivityDto dto = new ActivityDto();
		dto.setActionId(from.getAction().getId());
		dto.setDate(from.getDate());
		dto.setId(from.getId());
		return dto;
	}
}
