package com.manos.prototype.service;


import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;

public interface UserService {

    User findByEmail(String email);

    void saveUser(User user);

	User getUser(long userId);

//	void updateUser(User user);

	void deleteUser(long userId);

	UserDetailsImpl getCurrentUser();

	String saveNewPassword(String email);
}
