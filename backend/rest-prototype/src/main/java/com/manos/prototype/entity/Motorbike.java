package com.manos.prototype.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "motorbikes")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Motorbike extends Vehicle{
	
	@Column(name="tyre_size")
	private double tyreSize;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	private MotorbikeType motorbikeType;
	
//	public Motorbike() {
//	}
//
//	public Motorbike(String make, String model, Color color, Calendar dateOfProduction, 
//			double price, EngineType engineType, double tyreSize, MotorbikeType type) {
//		super(make, model, color, dateOfProduction, price);
//		this.setEngineType(engineType);
//		this.tyreSize = tyreSize;
//		this.type = type;
//	}

	public double getTyreSize() {
		return tyreSize;
	}

	public void setTyreSize(double tyreSize) {
		this.tyreSize = tyreSize;
	}

	public MotorbikeType getMotorbikeType() {
		return motorbikeType;
	}

	public void setMotorbikeType(MotorbikeType motorbikeType) {
		this.motorbikeType = motorbikeType;
	}

	@Override
	public String toString() {
		return "Motorbike [tyreSize=" + tyreSize + ", motorbikeType=" + motorbikeType + " " + super.toString();
	}

}
