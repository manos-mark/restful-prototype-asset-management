package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Car;

public interface CarDAO {
	
	public void saveCar(Car car);

	public Car getCar(int theId);

	public void deleteCar(int theId);

	public List<Car> getCars();

	List<Car> getCars(int theId);

}
