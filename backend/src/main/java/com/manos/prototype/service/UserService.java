package com.manos.prototype.service;


import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;

public interface UserService {

    void saveUser(User user);
    
    void updateUser(User user, String oldPass, String newPass);

	User getUser(long userId);

	void deleteUser(long userId);

	UserDetailsImpl getCurrentUserDetails();

	String saveNewPassword(String email);

}
