package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
@Sql(scripts = "classpath:/sql/pictures.sql")
public class ProductPictureControllerTest extends AbstractMvcTest{

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("productController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}
	
	@Test
	public void getProductsPicture_jpeg_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/product-pictures/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentType())
			.isEqualTo(MediaType.IMAGE_JPEG_VALUE);
	}
	
	@Test
	public void getProductsPicture_png_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/product-pictures/{id}", 2)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentType())
			.isEqualTo(MediaType.IMAGE_PNG_VALUE);
	}
	
	@Test
	public void getProductsPicture_gif_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/product-pictures/{id}", 3)
			.contentType(MediaType.APPLICATION_JSON);

		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentType())
			.isEqualTo(MediaType.IMAGE_GIF_VALUE);
	}
	
//	@Test
//	public void getProductsPicture_fail() throws Exception {
//		MockHttpServletRequestBuilder request = get("/product-pictures/{id}", 1)
//			.contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult mvcResult = mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isOk())
//			.andReturn();
//		
//		assertThat(mvcResult.getResponse().getContentType())
//			.isNotEqualTo(MediaType.IMAGE_GIF_VALUE)
//			.isNotEqualTo(MediaType.IMAGE_JPEG_VALUE)
//			.isNotEqualTo(MediaType.IMAGE_PNG_VALUE);
//	}
}
