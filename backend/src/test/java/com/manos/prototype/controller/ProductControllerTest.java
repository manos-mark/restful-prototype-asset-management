package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@Sql(scripts = "classpath:/sql/status.sql")
@Sql(scripts = "classpath:/sql/projects.sql")
@Sql(scripts = "classpath:/sql/products.sql")
@Sql(scripts = "classpath:/sql/pictures.sql")
public class ProductControllerTest extends AbstractMvcTest{
	
//	@Autowired
//	private ProductTestService productTestService;

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("productController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}
	
	@Test
	public void getProductsPaginated_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
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
		MockHttpServletRequestBuilder request = get("/products/")
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
	public void getProductsPaginated_wrongStatusFilter_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
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
	public void getProductsPaginated_withProjectNameFilter_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "date")
			.param("projectName", "firstProject");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProductsPaginated_withDateFilter_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
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
	public void getProductsPaginated_wrongDateFilter_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
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
	public void getProductsPaginated_emptyField_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProductsPaginated_nullField_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProductsCountByStatus_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/count/{id}",2)
			.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProductsCountByStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/count/{id}",5)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProductById_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}",1)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProductById_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}",-5)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteProduct_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/products/{id}", 1)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProduct_fail() throws Exception {
		MockHttpServletRequestBuilder request = delete("/products/{id}", 0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isNotFound());
	}
	

//	@Test
//	public void updateProduct_success() throws Exception {
//		MockMultipartFile pic = new MockMultipartFile("pic", "pic.jpg", "multipart/form-data", "some xml".getBytes());
//
//		MockHttpServletRequestBuilder request = put("/products/{id}", 1)
//				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//				.accept(MediaType.MULTIPART_FORM_DATA_VALUE)
//				.content("{"
//						+ "\"productName\": \"changed\","
//						+ "\"serialNumber\": \"serialNumber\","
//						+ "\"description\": \"description\","
//						+ "\"quantity\": \"12\","
//						+ "\"statusId\": \"2\","
//						+ "\"thumbPictureIndex\": \"0\","
//		        		+ "\"projectId\": \"1\""
//		        		+ "}");
//		
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isOk());
//		
//		Product savedProduct = productTestService.getProduct(16);
//		assertThat(savedProduct.getDescription()).isEqualTo("description");
//		assertThat(savedProduct.getProductName()).isEqualTo("changed");
//		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
//		assertThat(savedProduct.getQuantity()).isEqualTo(12);
//		assertThat(savedProduct.getSerialNumber()).isEqualTo("serialNumber");
//		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
//	}
//	
//	@Test
//	public void updateProduct_fail() throws Exception {
//		MockHttpServletRequestBuilder request = put("/products/{id}", 12)
//			.contentType(MediaType.APPLICATION_JSON);
//		
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isBadRequest());
//	}
	
//	@Test
//	public void addProduct_success() throws Exception {
//		MockHttpServletRequestBuilder request = post("/products")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content("{"
//					+ "\"productName\": \"productName\","
//					+ "\"serialNumber\": \"serialNumber\","
//					+ "\"description\": \"description\","
//					+ "\"quantity\": \"12\","
//					+ "\"statusId\": \"2\","
//	        		+ "\"projectId\": \"1\""
//	        		+ "}");
//
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isOk());
//		
//		Product savedProduct = productTestService.getLastProduct();
//		assertThat(savedProduct.getDescription()).isEqualTo("description");
//		assertThat(savedProduct.getProductName()).isEqualTo("productName");
//		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
//		assertThat(savedProduct.getQuantity()).isEqualTo(12);
//		assertThat(savedProduct.getSerialNumber()).isEqualTo("serialNumber");
//		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
//	}
//	
//	@Test
//	public void addProduct_fail() throws Exception {
//		MockHttpServletRequestBuilder request = post("/products")
//			.contentType(MediaType.APPLICATION_JSON);
//
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isBadRequest());
//	}
//	
	@Test
	public void getPicturesByProductId_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/pictures", 1)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getPicturesByProductId_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/pictures", -1)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isBadRequest());
	}
}














