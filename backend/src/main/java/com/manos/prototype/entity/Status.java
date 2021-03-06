package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "e_status")
public class Status {

	public static final int IN_PROGRESS = 1;
	public static final int NEW_ID = 2;
	public static final int FINISHED = 3;
	
	@Id
	@Column(name="id")
	private int id;

	@Column(name = "status")
	private String status;

	public Status(int id, String status) {
		this.id = id;
		this.status = status;
	}
	
	public Status(int id) {
		this.id = id;
	}

	public Status() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
