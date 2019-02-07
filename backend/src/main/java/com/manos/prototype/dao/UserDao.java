package com.manos.prototype.dao;

import com.manos.prototype.entity.User;

public interface UserDao {

    User getUserByEmail(String email);
    
    void saveUser(User user);

	User getUserDaoById(long userId);

	void deleteUser(long userId);
    
}
