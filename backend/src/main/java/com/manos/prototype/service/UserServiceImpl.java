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

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserDetailsImpl getCurrentUser() {
		
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		
		if (userDetails == null) {
			throw new EntityNotFoundException("User not found");
		}
		return userDetails;
	}
	
	@Override
	@Transactional
	public User findByEmail(String email) {
		// check the database if the user already exists
		User user = userDao.getUserByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User email not found - " + email); // throw
		}
		return user;
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		if (findByEmail(user.getEmail()) != null) {
			throw new EntityNotFoundException("User name already exists - " + user.getEmail(), new Exception());
		}
//		user.setId(Long.parseLong("0"));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		try {
			userDao.saveUser(user);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
	}

	@Override
	@Transactional
	public User getUser(long userId) {
		return userDao.getUserDaoById(userId);
	}

//	@Override
//	@Transactional
//	public void updateUser(User user) {
//		
//		if (user == null) {
//			throw new EntityNotFoundException("User id not found");
//		}
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		
//		try {
//			userDao.saveUser(user);
//		} catch (Exception e) {
//			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
//		}
//	}

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
