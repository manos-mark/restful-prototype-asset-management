package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void saveUser(User user);

	List<User> getUsers();

	User getUser(long userId);

	void updateUser(User user);

	void deleteUser(long userId);
    
}
