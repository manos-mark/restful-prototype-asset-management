package com.manos.prototype.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.EmailRequestDto;
import com.manos.prototype.dto.UserDto;
import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.manos.prototype.service.EmailService;
import com.manos.prototype.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/current")
	public UserDto getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@PostMapping("/new-password")
	public void newPassword(@RequestParam EmailRequestDto email) throws MessagingException {
		String newPassword;
		newPassword = userService.saveNewPassword(email);		// change pass
		emailService.sendNewPassword(email.getEmail(), newPassword); 	// send email
	}
	
	@PostMapping
	public User addUser(@RequestBody UserRequestDto userDTO) {
		return userService.saveUser(userDTO);
	}
	
	@PutMapping
	public User updateUser(@RequestBody UserRequestDto userDTO) {
		return userService.updateUser(userDTO);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable long userId) {
		return "Deleted user with id - " + userId;
	}
}
















