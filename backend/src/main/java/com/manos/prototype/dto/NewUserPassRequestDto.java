package com.manos.prototype.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.manos.prototype.validation.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class NewUserPassRequestDto {

	@NotNull
	@Size(min = 1, message = "is required")
	private String oldPassword;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String matchingPassword;

	public NewUserPassRequestDto() {
	}

	public NewUserPassRequestDto(@NotNull @Size(min = 1, message = "is required") String oldPassword,
			@NotNull(message = "is required") @Size(min = 1, message = "is required") String password,
			@NotNull(message = "is required") @Size(min = 1, message = "is required") String matchingPassword) {
		this.oldPassword = oldPassword;
		this.password = password;
		this.matchingPassword = matchingPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	
	
}
