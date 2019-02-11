package com.manos.prototype.service;


import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;

public interface UserService {

    void saveUser(User user);

	User getUser(long userId);


	void deleteUser(long userId);

	UserDetailsImpl getCurrentUserDetails();

	String saveNewPassword(String email);

	void updateUser(User user);
}
