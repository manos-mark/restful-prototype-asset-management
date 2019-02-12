package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.manos.prototype.entity.User;
import com.manos.prototype.testservice.UserTestService;

public class UserControllerTest extends AbstractMvcTest {

	@Autowired
	private UserTestService userTestService;
	
	private Long testUserId;
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("userController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}

	@Test
	public void getCurrentUserAuthorization() throws Exception {
		MockHttpServletRequestBuilder request = get("/users/current");
		
		mockMvc.perform(request)
			.andExpect(status().isUnauthorized());

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)))
			.andExpect(status().isOk())
			.andReturn();

		assertThat("application/json;charset=UTF-8")
			.isEqualTo(mvcResult.getResponse().getContentType());
	}
	
	@Test
	public void addUser_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{"
		        		+ "\"firstName\": \"firstName\","
		        		+ "\"lastName\": \"lastName\","
		        		+ "\"email\": \"email@email.com\","
		        		+ "\"password\": \"123\""
		        		+ "}");
				
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		User savedUser = userTestService.getLastUser();
		testUserId = savedUser.getId();
		assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
		assertThat(savedUser.getLastName()).isEqualTo("lastName");
		assertThat(savedUser.getPassword()).isEqualTo("$2a$10$erHpDkQD9jHMD1dFQ.kcsu4fYko2cxvx/EWgP/KEeuCO07RVfRAfa");
	}
	
	@Test
	public void addUser_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/users")
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{"
		        		+ "\"email\": \"email@email.com\","
		        		+ "\"password\": \"123\""
		        		+ "}");
				
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isNotFound());	
	}
	
	@Test
	public void newPassword_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/users/new-password")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("email", "email@email.com");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		String oldPassword = user.getPassword();
		String newPassword = userTestService.getLastUser().getPassword();
		assertThat(oldPassword).isNotEqualTo(newPassword);
	}
	
	@Test
	public void newPassword_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/users/new-password")
			.contentType(MediaType.APPLICATION_JSON)
	        .param("email", "");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void updateUser_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/users/{id}", testUserId)
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{"
		        		+ "\"firstName\": \"firstName\","
		        		+ "\"lastName\": \"lastName\","
		        		+ "\"email\": \"changed@email.com\","
		        		+ "\"password\": \"123\""
		        		+ "}");				
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		User savedUser = userTestService.getLastUser();
		assertThat(savedUser.getEmail()).isEqualTo("changed@email.com");
		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
		assertThat(savedUser.getLastName()).isEqualTo("lastName");
	}
	
	@Test
	public void updateUser_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/users/{id}", testUserId)
				.contentType(MediaType.APPLICATION_JSON);
				
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteUser_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/users/{id}", testUserId);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteUser_fail() throws Exception {
		MockHttpServletRequestBuilder request = delete("/users/{id}", "");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
}








