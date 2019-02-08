package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.UserDto;
import com.manos.prototype.entity.User;
import com.pastelstudios.convert.Converter;

@Component
public class UserDtoToUserConveret implements Converter<UserDto, User> {

	@Override
	public User convert(UserDto from) {
		User entity = new User();
		BeanUtils.copyProperties(from, entity);
		return entity;
	}
	
}
