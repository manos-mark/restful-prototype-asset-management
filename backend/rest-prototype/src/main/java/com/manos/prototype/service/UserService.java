package com.manos.prototype.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.manos.prototype.dto.UserDTO;
import com.manos.prototype.entity.Role;
import com.manos.prototype.entity.User;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void saveUser(User user, UserDTO userDTO);

	List<Role> getRoles();

	List<User> getUsers();

	User getUser(long userId);

	void updateUser(User user, UserDTO userDTO);

	void deleteUser(long userId);
}
