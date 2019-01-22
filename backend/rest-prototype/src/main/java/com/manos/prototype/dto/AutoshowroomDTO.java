package com.manos.prototype.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AutoshowroomDTO {

	@Min(value = 1, message = "must be greater than 0")
	private Integer id;
	
	@NotNull(message = "is required")
	@Size(min = 1, message = "is required")
	private String name;
	
	private String imagePath;

	public Integer getId() {
		return id;
	}

	public void setIn(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
		
}
