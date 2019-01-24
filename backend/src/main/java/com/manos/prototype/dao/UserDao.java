package com.manos.prototype.dao;

import com.manos.prototype.entity.User;

public interface UserDao {

    User findByUserEmail(String email);
    
    void saveUser(User user);

	User getUser(long userId);

	void updateUser(User user);

	void deleteUser(long userId);
    
}
