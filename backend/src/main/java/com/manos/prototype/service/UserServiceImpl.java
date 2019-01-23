package com.manos.prototype.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.RoleDao;
import com.manos.prototype.dao.UserDao;
import com.manos.prototype.dto.UserDTO;
import com.manos.prototype.entity.Role;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUserName(String userName) {
		// check the database if the user already exists
		User user = userDao.findByUserName(userName);
		return user;
	}

	@Override
	@Transactional
	public User saveUser(UserDTO userDTO) {
		if (findByUserName(userDTO.getUserName()) != null) {
			throw new EntityNotFoundException("User name already exists - " + userDTO.getUserName(), new Exception());
		}
		User tempUser = new User();
		tempUser.setId(Long.parseLong("0"));
		tempUser.setEmail(userDTO.getEmail());
		tempUser.setFirstName(userDTO.getFirstName());
		tempUser.setLastName(userDTO.getLastName());
		tempUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		tempUser.setUserName(userDTO.getUserName());
		
		// give roles to the user
		Collection<Role> tempColl = new ArrayList<>(); 
		tempColl.add( roleDao.findRoleByName(userDTO.getRole().toString()) );
		if ( !userDTO.getRole().toString().equals("ROLE_EMPLOYEE") ) {
			tempColl.add(roleDao.findRoleByName("ROLE_EMPLOYEE")); // give default role
		}
		
		tempUser.setRoles(tempColl);
		 // save user in the database
		try {
			userDao.saveUser(tempUser);			
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return tempUser;
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<Role> getRoles() {
		return roleDao.getRoles();
	}

	@Override
	@Transactional
	public User getUser(long userId) {
		return userDao.getUser(userId);
	}

	@Override
	@Transactional
	public User updateUser(UserDTO userDTO) {
		User tempUser = getUser(userDTO.getId());
		
		if (tempUser == null) {
			throw new EntityNotFoundException("User id not found - " + userDTO.getId());
		}
//		User user = new User();
		tempUser.setId((long)userDTO.getId());
		tempUser.setEmail(userDTO.getEmail());
		tempUser.setFirstName(userDTO.getFirstName());
		tempUser.setLastName(userDTO.getLastName());
		tempUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		tempUser.setUserName(userDTO.getUserName());
		
		// give roles to the user
		Collection<Role> tempColl = new ArrayList<>(); 
		tempColl.add( roleDao.findRoleByName(userDTO.getRole().toString()) );
		if ( !userDTO.getRole().toString().equals("ROLE_EMPLOYEE") ) {
			tempColl.add(roleDao.findRoleByName("ROLE_EMPLOYEE")); // give default role
		}
		
		tempUser.setRoles(tempColl);
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
