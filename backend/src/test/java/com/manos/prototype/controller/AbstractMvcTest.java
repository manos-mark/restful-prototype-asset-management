package com.manos.prototype.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.manos.prototype.config.AppConfigIntegrationTest;
import com.manos.prototype.config.SecurityConfig;
import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigIntegrationTest.class, SecurityConfig.class })
@WebAppConfiguration
@Transactional
public class AbstractMvcTest {

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;
	
	protected UserDetailsImpl user = initUser();
	
	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders
					.webAppContextSetup(wac)
					.apply(SecurityMockMvcConfigurers.springSecurity())
					.build();
	}

	private UserDetailsImpl initUser() {
		User user = new User();
		user.setEmail("susan@luv2code.com");
		user.setFirstName("Susan");
		user.setLastName("Adams");
		user.setId(3L);
		user.setPassword("$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K");
		user.setAcceptedCookiesDatetime(null);
		
		return new UserDetailsImpl(user);
	}
}
