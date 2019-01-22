package com.manos.prototype.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "autoshowrooms")
public class Autoshowroom {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "imagePath")
	private String imagePath;
	
//	@OneToMany(mappedBy = "autoshowroom")
//	private List<Vehicle> availableVehicles;
//	
//	@OneToMany(mappedBy = "autoshowroom")
//	private List<Vehicle> soldVehicles;
	
	public Autoshowroom() {
	}

	public Autoshowroom(int id) {
		this.id = id;
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
	
	
	
//	public List<Vehicle> getSoldVehicles() {
//		return soldVehicles;
//	}
//
//	public void setSoldVehicles(List<Vehicle> soldVehicles) {
//		this.soldVehicles = soldVehicles;
//	}
//	
//	
//	public List<Vehicle> getAvailableVehicles() {
//		return availableVehicles;
//	}
//
//	public void setAvailableVehicles(List<Vehicle> availableVehicles) {
//		this.availableVehicles = availableVehicles;
//	}
	
//	// add a convenience method
//	public void sellVehicle(Vehicle vehicle) {
//		if (soldVehicles == null) {
//			soldVehicles = new ArrayList<>();
//		}
//			vehicle.setAutoshowroom(this);
//			soldVehicles.add(vehicle);
//	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Autoshowroom [id=" + id + ", name=" + name + "]";
	}
	
}
