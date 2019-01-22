package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Car;

public interface CarService {

	void saveCar(Car car);

	Car getCar(int theId);

	void deleteCar(int carId);

	List<Car> getCars(int autoshowroomId);
	
}
