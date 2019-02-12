package com.manos.prototype.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
//	@Autowired
//	private GenericFinder finder;
//	
//	@Autowired
//	private GenericGateway gateway;
	
	@Override
	@Transactional
	public UserDetailsImpl getCurrentUserDetails() {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		
		if (userDetails == null) {
			throw new EntityNotFoundException("User not found");
		}
		return userDetails;
	}
	
	@Override
	@Transactional
	public void saveUser(User user) {
//		if (userDao.getUserByEmail(user.getEmail()) != null) {
//			throw new EntityNotFoundException("Email already exists - " + user.getEmail(), new Exception());
//		}
		
		user.setId(Long.parseLong("0"));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if (user.getPassword() == null) {
			throw new EntityNotFoundException("User password cannot be null");
		}
		
		try {
			userDao.saveUser(user);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public User getUser(long userId) {
		return userDao.getUserById(userId);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		if (user == null) {
			throw new EntityNotFoundException("User not found");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if (user.getPassword() == null) {
			throw new EntityNotFoundException("User password cannot be null");
		}
		
		try {
			userDao.updateUser(user);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public void deleteUser(long userId) {
		User tempUser = getUser(userId);
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userId);
		}
		userDao.deleteUser(userId);
	}

	@Override
	@Transactional
	public String saveNewPassword(String email) {
		
		// check first if the mail exist in db
		User user = this.userDao.getUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User email not found - " + email); // throw
		}
		
		// generate new password
		String newPassword = PasswordGenerationUtil.getSaltString();

		// save password to db
		user.setPassword(passwordEncoder.encode(newPassword));
		try {
			userDao.saveUser(user);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return newPassword;
	}
}
