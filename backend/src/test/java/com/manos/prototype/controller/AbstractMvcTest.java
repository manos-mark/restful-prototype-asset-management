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
import org.springframework.web.context.WebApplicationContext;

import com.manos.prototype.config.AppConfigIntegrationTest;
import com.manos.prototype.config.SecurityConfig;
import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigIntegrationTest.class, SecurityConfig.class })
@WebAppConfiguration
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
		user.setEmail("test@mail.com");
		user.setFirstName("test");
		user.setLastName("test");
		user.setId(1L);
		user.setPassword("test");
		
		return new UserDetailsImpl(user);
	}
}
