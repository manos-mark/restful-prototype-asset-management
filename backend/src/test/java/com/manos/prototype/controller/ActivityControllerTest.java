package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Sql(scripts = "classpath:/sql/actions.sql")
@Sql(scripts = "classpath:/sql/users.sql")
@Sql(scripts = "classpath:/sql/activities.sql")
public class ActivityControllerTest extends AbstractMvcTest{

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("userController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}
	
	@Test
	public void getActivities_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/activities")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addActivity_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/activities")
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{"
		        		+ "\"actionId\": \"1\""
		        		+ "}")
		        .accept(MediaType.APPLICATION_JSON_UTF8);
			
			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addActivity_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/activities")
				.contentType(MediaType.APPLICATION_JSON)
		        .content("{"
		        		+ "\"actionId\": \"9\""
		        		+ "}")
		        .accept(MediaType.APPLICATION_JSON_UTF8);
			
			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isBadRequest());
	}
}
