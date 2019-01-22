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
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public void saveUser(User user, UserDTO userDTO) {
		// give roles to the user
		Collection<Role> tempColl = new ArrayList<>(); 
		tempColl.add( roleDao.findRoleByName(userDTO.getRole().toString()) );
		if ( !userDTO.getRole().toString().equals("ROLE_EMPLOYEE") ) {
			tempColl.add(roleDao.findRoleByName("ROLE_EMPLOYEE")); // give default role
		}
		
		user.setRoles(tempColl);
		 // save user in the database
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
//		System.out.println(user.getRoles());
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles())
			);
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
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@Override
	@Transactional
	public User getUser(long userId) {
		return userDao.getUser(userId);
	}

	@Override
	@Transactional
	public void updateUser(User user, UserDTO userDTO) {
		// give roles to the user
		Collection<Role> tempColl = new ArrayList<>(); 
		tempColl.add( roleDao.findRoleByName(userDTO.getRole().toString()) );
		if ( !userDTO.getRole().toString().equals("ROLE_EMPLOYEE") ) {
			tempColl.add(roleDao.findRoleByName("ROLE_EMPLOYEE")); // give default role
		}
		
		user.setRoles(tempColl);
		 // save user in the database
		userDao.updateUser(user);
	}

	@Override
	@Transactional
	public void deleteUser(long userId) {
		userDao.deleteUser(userId);
	}
}
