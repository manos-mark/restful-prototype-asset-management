package com.manos.prototype.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PictureTypeRequestDto {
	
	public static final String TYPE_NEW = "new";
	public static final String TYPE_EXISTING = "existing";
	public static final String TYPE_DELETED = "deleted";
	
	@NotNull
	@Min(0)
	private int pictureId;
	
	@NotNull
	@Pattern(regexp = "(" + TYPE_NEW + "|" + TYPE_EXISTING + "|" +  TYPE_DELETED + ")" )
	private String type;

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
