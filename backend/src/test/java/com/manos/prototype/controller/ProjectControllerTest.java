package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.manos.prototype.entity.Project;
import com.manos.prototype.testservice.ProjectTestService;

public class ProjectControllerTest extends AbstractMvcTest{

	@Autowired
	private ProjectTestService projectTestService;
	
	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("projectController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}
	
	@Test
	public void getProjectsCountByStatus_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects/count")
			.contentType(MediaType.APPLICATION_JSON)
	        .content("{"
	        		+ "\"statusId\": \"1\""
	        		+ "}");
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProjectsCountByStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects/count")
			.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addProject_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"date\": \"2018-12-17\","
					+ "\"projectName\": \"projectName\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManager\": \"projectManager\","
					+ "\"statusId\": \"2\""
	        		+ "}");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		Project savedProject = projectTestService.getLastProject();
		assertThat(savedProject.getCompanyName()).isEqualTo("companyName");
		assertThat(savedProject.getDate()).isEqualTo("2018-12-17");
		assertThat(savedProject.getProjectManager()).isEqualTo("projectManager");
		assertThat(savedProject.getProjectName()).isEqualTo("projectName");
		assertThat(savedProject.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	public void addProject_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects")
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 3)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"date\": \"2018-12-17\","
					+ "\"projectName\": \"test\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManager\": \"projectManager\","
					+ "\"statusId\": \"2\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		Project savedProject = projectTestService.getProject(3);
		assertThat(savedProject.getCompanyName()).isEqualTo("companyName");
		assertThat(savedProject.getDate()).isEqualTo("2018-12-17");
		assertThat(savedProject.getProjectManager()).isEqualTo("projectManager");
		assertThat(savedProject.getProjectName()).isEqualTo("test");
		assertThat(savedProject.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	public void updateProject_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 2)
			.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteProject_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/projects/{id}", 2)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProject_fail() throws Exception {
		MockHttpServletRequestBuilder request = delete("/projects/{id}", 0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProject_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("firstProject")))
			.andExpect(content().string(containsString("firstCompany")))
			.andExpect(content().string(containsString("firstProjectManager")))
			.andExpect(content().string(containsString("2")))
			.andExpect(content().string(containsString("2011-12-17")))
			.andReturn();
		
		assertThat("application/json;charset=UTF-8")
			.isEqualTo(mvcResult.getResponse().getContentType());
	}
	
	@Test
	public void getProject_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}", 0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
}








