package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.entity.Activity;
import com.pastelstudios.convert.Converter;

@Component
public class ActivityToActivityDtoConverter implements Converter<Activity, ActivityDto> {

	@Override
	public ActivityDto convert(Activity from) {
		ActivityDto dto = new ActivityDto();
		BeanUtils.copyProperties(from, dto);
		return dto;
	}
}
