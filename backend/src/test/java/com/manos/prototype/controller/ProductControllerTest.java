package com.manos.prototype.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.manos.prototype.entity.Product;
import com.manos.prototype.testservice.ProductTestService;

public class ProductControllerTest extends AbstractMvcTest{
	
	@Autowired
	private ProductTestService productTestService;

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		assertThat(mockMvc).isNotNull();
		assertThat(servletContext).isNotNull();
		assertThat(wac.getBean("productController")).isNotNull();
		assertThat(servletContext instanceof MockServletContext).isTrue();
	}
	
	@Test
	public void getProductsCountByStatus_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/products/count")
			.contentType(MediaType.APPLICATION_JSON)
	        .content("{"
	        		+ "\"statusId\": \"1\""
	        		+ "}");
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getProductsCountByStatus_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/products/count")
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void addProduct_success() throws Exception {
		MockHttpServletRequestBuilder request = post("/products")
			.contentType(MediaType.APPLICATION_JSON)
			.content("{"
					+ "\"date\": \"2018-12-17\","
					+ "\"productName\": \"productName\","
					+ "\"serialNumber\": \"serialNumber\","
					+ "\"description\": \"description\","
					+ "\"quantity\": \"12\","
					+ "\"statusId\": \"2\","
	        		+ "\"projectId\": \"1\""
	        		+ "}");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		Product savedProduct = productTestService.getLastProduct();
		assertThat(savedProduct.getDate()).isEqualTo("2018-12-17");
		assertThat(savedProduct.getDescription()).isEqualTo("description");
		assertThat(savedProduct.getProductName()).isEqualTo("productName");
		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
		assertThat(savedProduct.getQuantity()).isEqualTo(12);
		assertThat(savedProduct.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	public void addProduct_fail() throws Exception {
		MockHttpServletRequestBuilder request = post("/products")
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void updateProduct_success() throws Exception {
		MockHttpServletRequestBuilder request = put("/products/{id}", 16)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"date\": \"2018-12-17\","
						+ "\"productName\": \"changed\","
						+ "\"serialNumber\": \"serialNumber\","
						+ "\"description\": \"description\","
						+ "\"quantity\": \"12\","
						+ "\"statusId\": \"2\","
		        		+ "\"projectId\": \"1\""
		        		+ "}");
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
		
		Product savedProduct = productTestService.getProduct(16);
		assertThat(savedProduct.getDate()).isEqualTo("2018-12-17");
		assertThat(savedProduct.getDescription()).isEqualTo("description");
		assertThat(savedProduct.getProductName()).isEqualTo("changed");
		assertThat(savedProduct.getProject().getId()).isEqualTo(1);
		assertThat(savedProduct.getQuantity()).isEqualTo(12);
		assertThat(savedProduct.getSerialNumber()).isEqualTo("serialNumber");
		assertThat(savedProduct.getStatus().getId()).isEqualTo(2);
	}
	
	@Test
	public void updateProduct_fail() throws Exception {
		MockHttpServletRequestBuilder request = put("/products/{id}", 12)
			.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void deleteProduct_success() throws Exception {
		MockHttpServletRequestBuilder request = delete("/products/{id}", 13)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
	@Test
	public void deleteProduct_fail() throws Exception {
		MockHttpServletRequestBuilder request = delete("/products/{id}", 0)
			.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProductsPaginated_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("field", "created")
			.param("statusId", "2");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isOk());
	}
	
//	@Test
//	public void getProductsPaginated_wrongField_fail() throws Exception {
//		MockHttpServletRequestBuilder request = get("/products/")
//			.contentType(MediaType.APPLICATION_JSON)
//			.param("page", "1")
//			.param("pageSize", "5")
//			.param("direction", "asc")
//			.param("field", "wrong")
//			.param("statusId", "2");
//
//		mockMvc.perform(request.with(user(user)).with(csrf()))
//			.andExpect(status().isBadRequest());
//	}
	
	@Test
	public void getProductsPaginated_field_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("direction", "asc")
			.param("statusId", "2");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getProductsPaginated_statusId_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/")
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "1")
			.param("pageSize", "5")
			.param("field", "created")
			.param("direction", "asc");

		mockMvc.perform(request.with(user(user)).with(csrf()))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPicturesByProductId_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/pictures", 1)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getPicturesByProductId_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/pictures", 0)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getThumbPictureByProductId_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/thumb-picture", 1)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void getThumbPictureByProductId_fail() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/thumb-picture", 0)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getPicturesCountByProductId_success() throws Exception {
		MockHttpServletRequestBuilder request = get("/products/{id}/pictures-count", 1)
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addPicture_success() throws Exception{
		MockHttpServletRequestBuilder request = post("/products/{id}/pictures", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"
						+ "\"picture\": \"x89504E470D0A1A0A0000000D494844520000001000000010080200000090916836000000017352474200AECE1CE90000000467414D410000B18F0BFC6105000000097048597300000EC300000EC301C76FA8640000001E49444154384F6350DAE843126220493550F1A80662426C349406472801006AC91F1040F796BD0000000049454E44AE426082\","
						+ "\"productId\": \"1\","
						+ "\"thumb\": \"false\""
		        		+ "}");

			mockMvc.perform(request.with(user(user)).with(csrf()))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void updatePicture_success() throws Exception{
	
	}
}














