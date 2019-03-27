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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.manos.prototype.entity.Project;
import com.manos.prototype.testservice.ProjectTestService;

@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
@Sql(scripts = "classpath:/sql/pictures.sql")
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
	public void getProjectsPaginated_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProductsPaginated_withStatusFilter_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date")
			.param("statusId", "1");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProjectsPaginated_wrongStatusFilter_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date")
			.param("statusId", "4");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectsPaginated_withDateFilter_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date")
			.param("fromDate", "10/12/1995");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProjectsPaginated_wrongDateFilter_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date")
			.param("fromDate", "10-12-1995");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectsPaginated_emptyField_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectsPaginated_nullField_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectsCountByStatus_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/count/{id}",3)
			.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProjectsCountByStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/count/{id}",5)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectById_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}",1)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProjectById_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}",-5)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteProject_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProject_fail() throws Exception {
		MockHttpServletRequestBuilder request = delete("/projects/{id}", 0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateProject_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"test\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"1\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		Project savedProject = projectTestService.getProject(1);
		assertThat(savedProject.getCompanyName()).isEqualTo("companyName");
		assertThat(savedProject.getProjectManager().getId()).isEqualTo(1);
		assertThat(savedProject.getProjectName()).isEqualTo("test");
		assertThat(savedProject.getStatus().getId()).isEqualTo(1);
	}
	
	@Test
	public void updateProject_nullProjectName_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"1\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_nullCompanyName_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"anme\","
					+ "\"companyName\": \"\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"1\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_nullManager_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"dasdasdas\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"\","
					+ "\"statusId\": \"1\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_wrongManager_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"dasdasdas\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"-1\","
					+ "\"statusId\": \"1\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_wrongStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"dasdasdas\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"4\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProject_nullStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/projects/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"dasdasdas\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"\""
	        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addProject_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"projectName\": \"projectName\","
					+ "\"companyName\": \"companyName\","
					+ "\"projectManagerId\": \"1\","
					+ "\"statusId\": \"1\""
	        		+ "}");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andDo(print())
			.andExpect(status().isOk());
		
		Project savedProject = projectTestService.getLastProject();
		assertThat(savedProject.getCompanyName()).isEqualTo("companyName");
		assertThat(savedProject.getProjectManager().getId()).isEqualTo(1);
		assertThat(savedProject.getProjectName()).isEqualTo("projectName");
		assertThat(savedProject.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	public void addProject_noRequestBodyFail() throws Exception {
		MockHttpServletRequestBuilder request = post("/projects")
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProjectManagers_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/project-managers")
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("project manager test")))
			.andExpect(content().string(containsString("1")))
			.andReturn();
		
		assertThat("application/json;charset=UTF-8")
			.isEqualTo(mvcResult.getResponse().getContentType());
	}
	
	@Test
	public void getProjects_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/all")
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProducts_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}/products",1)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProducts_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/projects/{id}/products",0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isNotFound());
	}
}








