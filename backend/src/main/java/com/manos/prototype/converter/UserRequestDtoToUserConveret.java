package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.pastelstudios.convert.Converter;

@Component
public class UserRequestDtoToUserConveret implements Converter<UserRequestDto, User> {

	@Override
	public User convert(UserRequestDto from) {
		User entity = new User();
		BeanUtils.copyProperties(from, entity);
		return entity;
	}
	
}
