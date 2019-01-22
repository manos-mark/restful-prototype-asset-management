package com.manos.prototype.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.manos.prototype.dto.UserDTO;
import com.manos.prototype.entity.Role;
import com.manos.prototype.entity.User;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User saveUser(UserDTO userDTO);

	List<Role> getRoles();

	User getUser(long userId);

	User updateUser(UserDTO userDTO);

	void deleteUser(long userId);
}
