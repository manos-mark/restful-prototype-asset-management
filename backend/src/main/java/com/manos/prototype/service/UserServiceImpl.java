package com.manos.prototype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.dto.UserDto;
import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.util.SecurityUtil;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserDto getCurrentUser() {
		UserDto currUser = new UserDto();
		currUser.setId(SecurityUtil.getCurrentUserDetails().getId());
		currUser.setFirstName(SecurityUtil.getCurrentUserDetails().getFirstName());
		currUser.setLastName(SecurityUtil.getCurrentUserDetails().getLastName());
		currUser.setEmail(SecurityUtil.getCurrentUserDetails().getEmail());
		return currUser;
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		// check the database if the user already exists
		User user = userDao.findByUserEmail(email);
		return user;
	}

	@Override
	@Transactional
	public User saveUser(UserRequestDto userDTO) {
		if (findByEmail(userDTO.getEmail()) != null) {
			throw new EntityNotFoundException("User name already exists - " + userDTO.getEmail(), new Exception());
		}
		User tempUser = new User();
		tempUser.setId(Long.parseLong("0"));
		tempUser.setEmail(userDTO.getEmail());
		tempUser.setFirstName(userDTO.getFirstName());
		tempUser.setLastName(userDTO.getLastName());
		tempUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		// save user in the database
		try {
			userDao.saveUser(tempUser);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return tempUser;
	}

	@Override
	@Transactional
	public User getUser(long userId) {
		return userDao.getUser(userId);
	}

	@Override
	@Transactional
	public User updateUser(UserRequestDto userDTO) {
		User tempUser = getUser(userDTO.getId());
		
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userDTO.getId());
		}
		tempUser.setId((long)userDTO.getId());
		tempUser.setEmail(userDTO.getEmail());
		tempUser.setFirstName(userDTO.getFirstName());
		tempUser.setLastName(userDTO.getLastName());
		tempUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		
		 // save user in the database
		try {
			userDao.updateUser(tempUser);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return tempUser;
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
}
