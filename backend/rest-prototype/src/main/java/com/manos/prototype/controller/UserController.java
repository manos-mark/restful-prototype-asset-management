package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable long userId) {
		User tempUser = userService.getUser(userId);
		
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userId);
		}
		return tempUser;
	}
	
	@PostMapping
	public User addUser(@RequestBody UserDTO userDTO) {
		if (userService.findByUserName(userDTO.getUserName()) != null) {
			throw new EntityNotFoundException("User name already exists - " + userDTO.getUserName(), new Exception());
		}
		User user = new User();
		user.setId(Long.parseLong("0"));
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//		user.setRoles(userDTO.getAuthorities()); // give the authorities in service impl
		user.setUserName(userDTO.getUserName());
		try {
			userService.saveUser(user, userDTO);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return user;
	}
	
	@PutMapping
	public User updateUser(@RequestBody UserDTO userDTO) {
		User tempUser = userService.getUser(userDTO.getId());
		
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userDTO.getId());
		}
		User user = new User();
		user.setId((long)userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//		user.setRoles(userDTO.getAuthorities()); // give the authorities in service impl
		user.setUserName(userDTO.getUserName());
		try {
			userService.updateUser(user, userDTO);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		
		return user;
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable long userId) {
		User tempUser = userService.getUser(userId);
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userId);
		}
		userService.deleteUser(userId);
		return "Deleted user with id - " + userId;
	}
}
















