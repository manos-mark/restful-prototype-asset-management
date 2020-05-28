package com.manos.prototype.converter;

import org.springframework.stereotype.Component;

import com.manos.prototype.controller.params.NewUserPassRequestParams;
import com.manos.prototype.entity.User;
import com.pastelstudios.convert.Converter;

@Component
public class NewUserPassRequestDtoToUserConveret implements Converter<NewUserPassRequestParams, User> {

	@Override
	public User convert(NewUserPassRequestParams from) {
		User entity = new User();
		entity.setPassword(from.getPassword());
		return entity;
	}
	
}
