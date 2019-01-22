package com.manos.prototype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_details")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="make")
	private String make;

	@Column(name="model")
	private String model;
	
	@Column(name="color")
	private String color;
	
	@Column(name="date_of_production")
	private String dateOfProduction;
	
	@Column(name="price")
	private double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "engine_type_id")
	private EngineType engineType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autoshowroom_id")
	private Autoshowroom autoshowroom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;
	
//	public Vehicle(String make, String model, Color color, Calendar dateOfProduction, double price) {
//		this.make = make;
//		this.model = model;
//		this.color = color;
//		this.dateOfProduction = dateOfProduction;
//		this.price = price;
//	}
//
//	public Vehicle() {
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDateOfProduction() {
		return dateOfProduction;
	}

	public void setDateOfProduction(String dateOfProduction) {
		this.dateOfProduction = dateOfProduction;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}
	
	public Autoshowroom getAutoshowroom() {
		return autoshowroom;
	}

	public void setAutoshowroom(Autoshowroom autoshowroom) {
		this.autoshowroom = autoshowroom;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public String toString() {
		return "id=" + id + ", make=" + make + ", model=" + model + ", color=" + color
				+ ", dateOfProduction=" + dateOfProduction + ", price=" + price + "]";
	}
	
}
