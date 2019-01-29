package com.manos.prototype.service;

import javax.mail.MessagingException;

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

	UserDto getCurrentUser();

	boolean newPassword(EmailRequestDto email) throws MessagingException;

}
