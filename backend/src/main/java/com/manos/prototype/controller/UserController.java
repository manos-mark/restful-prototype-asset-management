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
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.service.EmailService;
import com.manos.prototype.service.UserService;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ConversionService conversionService;
	
	@GetMapping("/current")
	public UserDto getCurrentUser() {
		UserDetailsImpl user = userService.getCurrentUserDetails();
		return conversionService.convert(user, UserDto.class);
	}
	
	@PostMapping("/new-password")
	public void newPassword(@RequestParam EmailRequestDto email) throws MessagingException {
		String newPassword;
		newPassword = userService.saveNewPassword(email.getEmail());		// change pass
		emailService.sendNewPassword(email.getEmail(), newPassword); 	// send email
	}
	
	@PostMapping
	public void addUser(@RequestBody UserRequestDto userRequestDto) {
		User user = conversionService.convert(userRequestDto, User.class);
		userService.saveUser(user);
	}
	
	@PutMapping
	public void updateUser(@RequestBody UserRequestDto userRequestDto) {
		User user = conversionService.convert(userRequestDto, User.class);
		userService.updateUser(user);
	}
	
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable long userId) {
		return "Deleted user with id - " + userId;
	}
}
















