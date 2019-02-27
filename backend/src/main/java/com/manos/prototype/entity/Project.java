package com.manos.prototype.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "creation_date")
	private String date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	private Status status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_manager_id")
	private ProjectManager projectManager;
	
	@OneToMany(mappedBy = "project")
	private List<Product> products = new ArrayList<>();
	
	public Project(String projectName, String companyName, ProjectManager projectManager, String date, Status status) {
		this.projectName = projectName;
		this.companyName = companyName;
		this.projectManager = projectManager;
		this.date = date;
		this.status = status;
	}
	
	public Project(int id) {
		this.id = id;
	}

	public Project() {}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
