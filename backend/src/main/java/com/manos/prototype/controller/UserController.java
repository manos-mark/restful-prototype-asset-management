package com.manos.prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.UserDTO;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// olo to bussines logic prepei na fygei apo ton controller kai na paei sto service
	
	@PostMapping
	public User addUser(@RequestBody UserDTO userDTO) {
		return userService.saveUser(userDTO);
	}
	
	@PutMapping
	public User updateUser(@RequestBody UserDTO userDTO) {
		return userService.updateUser(userDTO);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable long userId) {
		return "Deleted user with id - " + userId;
	}
}
















