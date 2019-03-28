package com.manos.prototype.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.controller.params.EmailRequestParams;
import com.manos.prototype.controller.params.NewUserPassRequestParams;
import com.manos.prototype.dto.UserDto;
import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.service.EmailServiceImpl;
import com.manos.prototype.service.UserServiceImpl;
import com.pastelstudios.convert.ConversionService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private ConversionService conversionService;
	
	@GetMapping("/current")
	public UserDto getCurrentUser() {
		UserDetailsImpl user = userService.getCurrentUserDetails();
		if (user == null) {
			return null;
		}
		return conversionService.convert(user, UserDto.class);
	}
	
//	@PostMapping
//	public void addUser(@Valid @RequestBody UserRequestDto userRequestDto) {
//		User user = conversionService.convert(userRequestDto, User.class);
//		user.setId(0L);
//		userService.saveUser(user);
//	}
//	
	@PostMapping("/reset-password")
	public void resetPassword(@Valid @RequestBody EmailRequestParams params) throws MessagingException {
		String newPassword;
		newPassword = userService.saveNewPassword(params.getEmail());		// change pass
		emailService.sendNewPassword(params.getEmail(), newPassword); 	// send email
	}

	@PutMapping("/new-password")
	public void updateUserPassword(@Valid @RequestBody NewUserPassRequestParams params) {
		userService.updateUserPassword(params.getOldPassword(), params.getPassword());
	}
	
	@PutMapping
	public void updateUser(@Valid @RequestBody UserRequestDto requestDto) {
		userService.updateUser(requestDto);
	}
	
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable("id") long userId) {
		userService.deleteUser(userId);
		return "Deleted user with id - " + userId;
	}
}
















