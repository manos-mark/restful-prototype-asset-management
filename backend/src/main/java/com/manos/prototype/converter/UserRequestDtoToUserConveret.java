package com.manos.prototype.converter;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.pastelstudios.convert.Converter;

@Component
public class UserRequestDtoToUserConveret implements Converter<UserRequestDto, User> {

	@Override
	public User convert(UserRequestDto from) {
		User entity = new User();

		entity.setEmail(from.getEmail());
		entity.setFirstName(from.getFirstName());
		entity.setLastName(from.getLastName());
		
		if (from.isAcceptedCookies()) {
			entity.setAcceptedCookiesDatetime(LocalDateTime.now());
		}
		
		return entity;
	}
	
}
