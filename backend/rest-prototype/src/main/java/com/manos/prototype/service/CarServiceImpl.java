package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.AutoshowroomDAO;
import com.manos.prototype.dao.CarDAO;
import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Car;

@Service
public class CarServiceImpl implements CarService {
	
	// need to inject the dao
	@Autowired
	private CarDAO carDAO;

	@Override
	@Transactional
	public void saveCar(Car car) {
		carDAO.saveCar(car);
	}

	@Override
	@Transactional
	public Car getCar(int theId) {
		return carDAO.getCar(theId);
	}

	@Override
	@Transactional
	public void deleteCar(int theId) {
		carDAO.deleteCar(theId);
	}

	@Override
	@Transactional
	public List<Car> getCars(int autoshowroomId) {
		return carDAO.getCars(autoshowroomId);
	}


}
