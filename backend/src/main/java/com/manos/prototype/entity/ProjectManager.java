package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "project_manager")
@Indexed
@Analyzer(definition = "customanalyzer")
public class ProjectManager {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name = "name")
	@Analyzer(definition = "customanalyzer")
	private String name;

	public ProjectManager(int id) {
		this.id = id;
	}
	
	public ProjectManager(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public ProjectManager() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
