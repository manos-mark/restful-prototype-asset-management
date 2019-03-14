package com.manos.prototype.dto;

public class UserDto {

	private Long id;

	private String firstName;

	private String lastName;

	private String email;
	
	private boolean acceptedCookies;

	public UserDto() {

	}
	
	public boolean isAcceptedCookies() {
		return acceptedCookies;
	}

	public void setAcceptedCookies(boolean acceptedCookies) {
		this.acceptedCookies = acceptedCookies;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
