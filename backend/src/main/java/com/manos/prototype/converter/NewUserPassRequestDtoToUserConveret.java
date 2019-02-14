package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.NewUserPassRequestDto;
import com.manos.prototype.entity.User;
import com.pastelstudios.convert.Converter;

@Component
public class NewUserPassRequestDtoToUserConveret implements Converter<NewUserPassRequestDto, User> {

	@Override
	public User convert(NewUserPassRequestDto from) {
		User entity = new User();
		entity.setPassword(from.getPassword());
		return entity;
	}
	
}
