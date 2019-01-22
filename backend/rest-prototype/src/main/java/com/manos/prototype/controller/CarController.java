package com.manos.prototype.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manos.prototype.dto.CarDTO;
import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Buyer;
import com.manos.prototype.entity.Car;
import com.manos.prototype.entity.CarType;
import com.manos.prototype.entity.EngineType;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.CarService;

@RestController
@RequestMapping("autoshowrooms/{autoshowroomId}/cars")
public class CarController {
	
	// need to inject the service
	@Autowired
	private CarService carService;

//	@GetMapping("autoshowrooms/{autoshowroomId}/cars/*") 
//	public void throwError() {
//		throw new EntityNotFoundException();
//	}
//	
	@GetMapping
	public List<Car> getCars(@PathVariable int autoshowroomId) {
		// get cars from the dao
		List<Car> cars = carService.getCars(autoshowroomId);
		// add them to the model
		return cars;
	}
	
	
	@GetMapping("/{carId}")
	public Car getCar(@PathVariable Integer carId) {
		
		// get car from the dao
		Car car = carService.getCar(carId);
		
		if (car == null) {
			throw new EntityNotFoundException("Car id not found - " + carId);
		}
		return car;
	}
	
	
	@PutMapping
	public Car updateCar(@RequestBody CarDTO carDTO) {
		// throw exception if car doesn't exists
		Car tempCar = carService.getCar(carDTO.getId());
		if (tempCar == null) {
			throw new EntityNotFoundException("Car id not found - " + carDTO.getId());
		}
		Car car = new Car();
		car.setId(carDTO.getId());
		car.setMake(carDTO.getMake());
		car.setModel(carDTO.getModel());
		car.setColor(carDTO.getColor());
		car.setDateOfProduction(carDTO.getDateOfProduction());
		car.setPrice(carDTO.getPrice());
		car.setAutoshowroom( new Autoshowroom(carDTO.getAutoshowroomId()) );
		car.setEngineType( new EngineType(carDTO.getEngineTypeId()) );
		car.setBuyer( new Buyer(carDTO.getBuyerId()) );
		car.setCarType( new CarType(carDTO.getCarTypeId()) );
		try {
			carService.saveCar(car);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return car;
	}

	
	@PostMapping
	public Car addCar(@RequestBody CarDTO carDTO) {
		Car car = new Car();
		car.setId(0);
		car.setMake(carDTO.getMake());
		car.setModel(carDTO.getModel());
		car.setColor(carDTO.getColor());
		car.setDateOfProduction(carDTO.getDateOfProduction());
		car.setPrice(carDTO.getPrice());
		car.setAutoshowroom( new Autoshowroom(carDTO.getAutoshowroomId()) );
		car.setEngineType( new EngineType(carDTO.getEngineTypeId()) );
		car.setBuyer( new Buyer(carDTO.getBuyerId()) );
		car.setCarType( new CarType(carDTO.getCarTypeId()) );
		try {
			carService.saveCar(car);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return car;
	}
	
	
	@DeleteMapping("/{carId}")
	public String deleteCar(@PathVariable int carId) {
		// throw exception if car doesn't exists
		Car tempCar = carService.getCar(carId);
		if (tempCar == null) {
			throw new EntityNotFoundException("Car id not found - " + carId);
		}
		carService.deleteCar(carId);
		return "Deleted car with id - " + carId;
	}
	
}
