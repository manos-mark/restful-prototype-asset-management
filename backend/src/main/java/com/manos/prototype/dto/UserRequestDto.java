package com.manos.prototype.dto;

import javax.validation.constraints.NotBlank;

import com.manos.prototype.validation.FieldMatch;
import com.manos.prototype.validation.ValidEmail;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserRequestDto {

	@NotBlank
	private String password;
	
	@NotBlank
	private String matchingPassword;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@ValidEmail
	@NotBlank
	private String email;
	
	private boolean acceptedCookies;

	public boolean isAcceptedCookies() {
		return acceptedCookies;
	}

	public void setAcceptedCookies(boolean acceptedCookies) {
		this.acceptedCookies = acceptedCookies;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
