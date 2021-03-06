package com.manos.prototype.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.ApplicationException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.finder.UserFinder;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;

@Service
public class UserServiceImpl {

	@Autowired
	private UserFinder userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GenericFinder finder;
	
	@Autowired
	private GenericGateway gateway;
	
	@Transactional
	public UserDetailsImpl getCurrentUserDetails() {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		if (userDetails == null) {
			return null;
		}
		
		User tempUser = finder.findById(User.class, userDetails.getId());
		if (tempUser.getAcceptedCookiesDatetime() != null) {
			userDetails.setAcceptedCookies(true);
		}
		return userDetails;
	}
	
//	@Transactional
//	public void saveUser(User user) {
//		if (user == null) {
//			throw new EntityNotFoundException(User.class);
//		}
//		if (userDao.getUserByEmail(user.getEmail()) != null) {
//			throw new ApplicationException("Email already exists");
//		}
//		user.setId(Long.parseLong("0"));
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		
//		if (user.getPassword() == null) {
//			throw new ApplicationException("Cannot save user password.");
//		}
//		sessionFactory.getCurrentSession().save(user);			
//	}

	@Transactional
	public User getUser(long userId) {
		return finder.findById(User.class, userId);
	}

	@Transactional
	public void updateUserPassword(String oldPassReq, String newPassReq) {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		if (userDetails == null) {
			throw new EntityNotFoundException(UserDetailsImpl.class);
		}
		
		User user = finder.findById(User.class, userDetails.getId());
		if (user == null) {
			throw new EntityNotFoundException(User.class);
		}
		
		if (passwordEncoder.matches(oldPassReq, user.getPassword())) {
			user.setPassword(passwordEncoder.encode(newPassReq));
			
			if (user.getPassword() == null) {
				throw new EntityNotFoundException(User.class);
			}
		} else {
			throw new ApplicationException("Could not save the new password.");
		}
	}

	@Transactional
	public void deleteUser(long userId) {
		User tempUser = this.getUser(userId);
		if (tempUser == null) {
			throw new EntityNotFoundException(User.class, userId);
		}
		gateway.delete(tempUser);
	}

	@Transactional
	public String saveNewPassword(String email) {
		// check first if the mail exist in db
		User user = userDao.getUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException(User.class); // throw
		}
		
		// generate new password
		String newPassword = PasswordGenerationUtil.getSaltString();

		// save password to db
		user.setPassword(passwordEncoder.encode(newPassword));
		gateway.save(user);
		return newPassword;
	}
	
	@Transactional
	public void updateUser(UserRequestDto user) {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		if (userDetails == null) {
			throw new EntityNotFoundException(UserDetailsImpl.class);
		}
		
		User oldUser = finder.findById(User.class, userDetails.getId());
		if (oldUser == null) {
			throw new EntityNotFoundException(User.class); // throw
		}
		oldUser.setEmail(user.getEmail());
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		
		if (user.isAcceptedCookies()) {
			oldUser.setAcceptedCookiesDatetime(LocalDateTime.now());
		}
	}
}
