package com.manos.prototype.service;


import java.time.LocalDateTime;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.UserDaoImpl;
import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;
import com.pastelstudios.db.GenericFinder;

@Service
public class UserServiceImpl {

	@Autowired
	private UserDaoImpl userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private GenericFinder finder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
//	@Autowired
//	private GenericGateway gateway;
	
	@Transactional
	public UserDetailsImpl getCurrentUserDetails() {
		UserDetailsImpl userDetails = SecurityUtil.getCurrentUserDetails();
		
		if (userDetails == null) {
			throw new EntityNotFoundException(User.class);
		}
		
		User tempUser = finder.findById(User.class, userDetails.getId());
		if (tempUser.getAcceptedCookiesDatetime() != null) {
			userDetails.setAcceptedCookies(true);
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
		
		sessionFactory.getCurrentSession().save(user);			
	}

	@Transactional
	public User getUser(long userId) {
		return finder.findById(User.class, userId);
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
		sessionFactory.getCurrentSession().delete(tempUser);
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
		sessionFactory.getCurrentSession().save(user);
		return newPassword;
	}
	
	@Transactional
	public void updateUser(UserRequestDto user, Long userId) {
		
		User oldUser = finder.findById(User.class, userId);
		if (oldUser == null) {
			throw new EntityNotFoundException(User.class); // throw
		}
		oldUser.setId(userId);
		
		oldUser.setEmail(user.getEmail());
		oldUser.setFirstName(user.getFirstName());
		oldUser.setLastName(user.getLastName());
		
		if (user.isAcceptedCookies()) {
			oldUser.setAcceptedCookiesDatetime(LocalDateTime.now());
		}
	}
}
