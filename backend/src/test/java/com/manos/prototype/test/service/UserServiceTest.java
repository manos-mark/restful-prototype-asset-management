package com.manos.prototype.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserDao userDaoMock;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Test
	public void getCurrentUserDto() {
		
	}
}
