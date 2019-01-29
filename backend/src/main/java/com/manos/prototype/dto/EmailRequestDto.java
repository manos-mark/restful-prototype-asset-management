package com.manos.prototype.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.manos.prototype.validation.ValidEmail;

public class EmailRequestDto {
	
	@ValidEmail
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String email;
	
	public EmailRequestDto () {}
	
	public EmailRequestDto (String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
