package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.manos.prototype.entity.User;
import com.manos.prototype.testservice.UserTestService;

@Sql(scripts = "classpath:/sql/users.sql")
public class UserControllerTest extends AbstractMvcTest {

	@Autowired
	private UserTestService userTestService;
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("userController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}

	@Test
	public void getCurrentUser_NoAuthorization() throws Exception {
		MockHttpServletRequestBuilder request = get("/users/current");
		
		MvcResult mvcResult = mockMvc.perform(request)
				.andExpect(status().isOk())
				.andReturn();
		
		assertThat(mvcResult.getResponse().getContentType())
			.isEqualTo(null);
	}
	
	@Test
	public void getCurrentUser_withAuthorization() throws Exception {
		MockHttpServletRequestBuilder request = get("/users/current");
		
		MvcResult mvcResult = mockMvc.perform(request.with(csrf()).with(user(user)))
			.andExpect(status().isOk())
			.andReturn();

		assertThat("application/json;charset=UTF-8")
			.isEqualTo(mvcResult.getResponse().getContentType());
	}
	
//	@Test
//	public void addUser_success() throws Exception {
//		MockHttpServletRequestBuilder request = post("/users")
//			.contentType(MediaType.APPLICATION_JSON)
//	        .content("{"
//	        		+ "\"firstName\": \"firstName\","
//	        		+ "\"lastName\": \"lastName\","
//	        		+ "\"email\": \"email@email.com\","
//	        		+ "\"password\": \"123\""
//	        		+ "}");
//				
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isOk());
//		
//		User savedUser = userTestService.getLastUser();
//		testUserId = savedUser.getId();
//		assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
//		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
//		assertThat(savedUser.getLastName()).isEqualTo("lastName");
//	}
//	
//	@Test
//	public void addUser_fail() throws Exception {
//		MockHttpServletRequestBuilder request = post("/users")
//			.contentType(MediaType.APPLICATION_JSON)
//	        .content("{"
//	        		+ "\"email\": \"email@email.com\","
//	        		+ "\"password\": \"123\""
//	        		+ "}");
//			
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isNotFound());	
//	}
	
	@Test
	public void resetPassword_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/users/reset-password")
			.contentType(MediaType.APPLICATION_JSON)
	        .content("{"
	        		+ "\"email\": \"susan@luv2code.com\""
	        		+ "}")
	        .accept(MediaType.APPLICATION_JSON_UTF8);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		String oldPassword = user.getPassword();
		String newPassword = userTestService.getLastUser().getPassword();
		assertThat(oldPassword).isNotEqualTo(newPassword);
	}
	
	@Test
	public void resetPassword_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/users/reset-password")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
	        		+ "\"email\": \"\""
	        		+ "}")
			.accept(MediaType.APPLICATION_JSON_UTF8);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void updateUserPassword_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/users/new-password")
			.contentType(MediaType.APPLICATION_JSON)
	        .content("{"
	        		+ "\"oldPassword\": \"fun123\","
	        		+ "\"password\": \"123123\","
	        		+ "\"matchingPassword\": \"123123\""
	        		+ "}")
	        .accept(MediaType.APPLICATION_JSON_UTF8);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
		
		String oldPassword = user.getPassword();
		String newPassword = userTestService.getLastUser().getPassword();
		assertThat(oldPassword).isNotEqualTo(newPassword);
	}
	
	@Test
	public void updateUser_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON_UTF8)
	        .content("{"
	        		+ "\"firstName\": \"firstName\","
	        		+ "\"lastName\": \"lastName\","
	        		+ "\"email\": \"changed@email.com\","
	        		+ "\"password\": \"123\","
	        		+ "\"matchingPassword\": \"123\""
	        		+ "}");				
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		User savedUser = userTestService.getUser(3L);
		assertThat(savedUser.getEmail()).isEqualTo("changed@email.com");
		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
		assertThat(savedUser.getLastName()).isEqualTo("lastName");
		assertThat(savedUser.getAcceptedCookiesDatetime()).isEqualTo((LocalDateTime) null);
	}
	
	@Test
	public void updateUser_acceptCookies_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/users")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON_UTF8)
	        .content("{"
	        		+ "\"firstName\": \"firstName\","
	        		+ "\"lastName\": \"lastName\","
	        		+ "\"email\": \"changed@email.com\","
	        		+ "\"password\": \"123\","
	        		+ "\"acceptedCookies\": \"true\","
	        		+ "\"matchingPassword\": \"123\""
	        		+ "}");				
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		User savedUser = userTestService.getUser(3L);
		assertThat(savedUser.getEmail()).isEqualTo("changed@email.com");
		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
		assertThat(savedUser.getLastName()).isEqualTo("lastName");
		assertThat(savedUser.getAcceptedCookiesDatetime()).isNotNull();
	}
	
	@Test
	public void updateUser_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/users/")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.content("{"
	        		+ "\"firstName\": \"\","
	        		+ "\"lastName\": \"\","
	        		+ "\"email\": \"\","
	        		+ "\"password\": \"\","
	        		+ "\"acceptedCookies\": \"\","
	        		+ "\"matchingPassword\": \"\""
	        		+ "}")
			.contentType(MediaType.APPLICATION_JSON);
				
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteUser_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/users/{id}", 3);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
}








