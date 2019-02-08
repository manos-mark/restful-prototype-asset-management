package com.manos.prototype.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.manos.prototype.dto.UserDto;
import com.manos.prototype.security.UserDetailsImpl;
import com.pastelstudios.convert.Converter;

@Component
public class UserDetailsToUserDtoConveret implements Converter<UserDetailsImpl, UserDto> {

	@Override
	public UserDto convert(UserDetailsImpl from) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(from, dto);
//		currUser.setId(userDetails.getId());
//		currUser.setFirstName(userDetails.getFirstName());
//		currUser.setLastName(userDetails.getLastName());
//		currUser.setEmail(userDetails.getEmail());
		return dto;
	}
	
}
