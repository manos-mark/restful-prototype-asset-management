package com.manos.prototype.testservice;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.entity.User;
import com.pastelstudios.db.GenericFinder;

@Service
public class UserTestService {

	@Autowired
	private GenericFinder finder;
	
	@Transactional(readOnly = true)
	public User getLastUser() {
		List<User> users = finder.findAll(User.class);
		return users.get(users.size() - 1);
	}
	
}
