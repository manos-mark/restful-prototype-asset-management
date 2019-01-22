package com.manos.prototype.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Car extends Vehicle{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private CarType carType;
	
//	public Car() {
//	}
//
//	public Car(String make, String model, Color color, Calendar dateOfProduction, 
//			double price, EngineType engineType, double tyreSize, CarType type) {
//		super(make, model, color, dateOfProduction, price);
//		this.setPrice(price);
//		this.setEngineType(engineType);
//	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	@Override
	public String toString() {
		return "Car [carType=" + carType + " " + super.toString();
	}

}
