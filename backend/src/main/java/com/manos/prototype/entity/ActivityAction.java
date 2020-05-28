package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "e_activity_action")
public class ActivityAction {

	@Id
	@Column(name="id")
	private int id;

	@Column(name = "action")
	private String action;

	public ActivityAction(int id, String action) {
		this.id = id;
		this.action = action;
	}

	public ActivityAction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
