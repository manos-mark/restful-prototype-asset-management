package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.dto.ActivityDto;
import com.manos.prototype.dto.EmailRequestDto;
import com.manos.prototype.dto.UserDto;
import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;

public interface UserService {

    User findByEmail(String email);

    User saveUser(UserRequestDto userDTO);

	User getUser(long userId);

	User updateUser(UserRequestDto userDTO);

	void deleteUser(long userId);

	UserDto getCurrentUserDto();

	String saveNewPassword(EmailRequestDto email);
}
