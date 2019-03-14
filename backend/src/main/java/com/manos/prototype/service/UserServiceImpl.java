package com.manos.prototype.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.UserDaoImpl;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;

@Service
public class UserServiceImpl {

	@Autowired
	private UserDaoImpl userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	@Autowired
//	private GenericFinder finder;
//	
//	@Autowired
//	private GenericGateway gateway;
	
	@Transactional
	public UserDetailsImpl getCurrentUserDetails() {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		
		if (userDetails == null) {
			throw new EntityNotFoundException(User.class);
		}
		return userDetails;
	}
	
	@Transactional
	public void saveUser(User user) {
//		if (userDao.getUserByEmail(user.getEmail()) != null) {
//			throw new EntityNotFoundException("Email already exists - " + user.getEmail(), new Exception());
//		}
		
		user.setId(Long.parseLong("0"));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if (user.getPassword() == null) {
			throw new EntityNotFoundException(User.class);
		}
		
		userDao.saveUser(user);			
	}

	@Transactional
	public User getUser(long userId) {
		return userDao.getUserById(userId);
	}

	@Transactional
	public void updateUser(User user, String oldPassReq, String newPassReq) {
		if (user == null) {
			throw new EntityNotFoundException(User.class);
		}
		
		String oldPass = SecurityUtil.getCurrentUserDetails().getPassword();
		
		if (passwordEncoder.matches(oldPassReq, oldPass)) {
			user.setPassword(passwordEncoder.encode(newPassReq));
			
			if (user.getPassword() == null) {
				throw new EntityNotFoundException(User.class);
			}
			
			userDao.updateUser(user);
		} else {
			throw new EntityNotFoundException(User.class);
		}
	}

	@Transactional
	public void deleteUser(long userId) {
		User tempUser = getUser(userId);
		if (tempUser == null) {
			throw new EntityNotFoundException(User.class, userId);
		}
		userDao.deleteUser(userId);
	}

	@Transactional
	public String saveNewPassword(String email) {
		// check first if the mail exist in db
		User user = this.userDao.getUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException(User.class); // throw
		}
		
		// generate new password
		String newPassword = PasswordGenerationUtil.getSaltString();

		// save password to db
		user.setPassword(passwordEncoder.encode(newPassword));
		userDao.saveUser(user);
		return newPassword;
	}
}
