package com.manos.prototype.controller.params;


import javax.validation.constraints.NotBlank;

import com.manos.prototype.validation.ValidEmail;

public class EmailRequestParams {
	
	@ValidEmail
	@NotBlank
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
