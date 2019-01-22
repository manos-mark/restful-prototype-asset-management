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

import com.manos.prototype.dto.MotorbikeDTO;
import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Buyer;
import com.manos.prototype.entity.EngineType;
import com.manos.prototype.entity.Motorbike;
import com.manos.prototype.entity.MotorbikeType;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.MotorbikeService;

@RestController
@RequestMapping("autoshowrooms/{autoshowroomId}/motorbikes")
public class MotorbikeController {
	
	// need to inject the service
	@Autowired
	private MotorbikeService motorbikeService;
	
	
	@GetMapping
	public List<Motorbike> getMotorbikes(@PathVariable int autoshowroomId) {
		// get motorbikes from the dao
		List<Motorbike> motorbikes = motorbikeService.getMotorbikes(autoshowroomId);
		// add them to the model
		return motorbikes;
	}
	
	
	@GetMapping("/{motorbikeId}")
	public Motorbike getMotorbike(@PathVariable int motorbikeId) {
		
		// get motorbike from the dao
		Motorbike motorbike = motorbikeService.getMotorbike(motorbikeId);
		
		if (motorbike == null) {
			throw new EntityNotFoundException("Motorbike id not found - " + motorbikeId);
		}
		return motorbike;
	}
	
	
	@PutMapping
	public Motorbike updateMotorbike(@RequestBody MotorbikeDTO motorbikeDTO) {
		// throw exception if motorbike doesn't exists
		Motorbike tempMotorbike = motorbikeService.getMotorbike(motorbikeDTO.getId());
		if (tempMotorbike == null) {
			throw new EntityNotFoundException("Motorbike id not found - " + motorbikeDTO.getId());
		}
		Motorbike motorbike = new Motorbike();
		motorbike.setId(motorbikeDTO.getId());
		motorbike.setMake(motorbikeDTO.getMake());
		motorbike.setModel(motorbikeDTO.getModel());
		motorbike.setColor(motorbikeDTO.getColor());
		motorbike.setDateOfProduction(motorbikeDTO.getDateOfProduction());
		motorbike.setPrice(motorbikeDTO.getPrice());
		motorbike.setAutoshowroom( new Autoshowroom(motorbikeDTO.getAutoshowroomId()) );
		motorbike.setEngineType( new EngineType(motorbikeDTO.getEngineTypeId()) );
		motorbike.setBuyer( new Buyer(motorbikeDTO.getBuyerId()) );
		motorbike.setMotorbikeType( new MotorbikeType(motorbikeDTO.getMotorbikeTypeId()) );
		motorbike.setTyreSize(motorbikeDTO.getTyreSize());
		try {
			motorbikeService.saveMotorbike(motorbike);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return motorbike;
	}

	@PostMapping
	public Motorbike addMotorbike(@RequestBody MotorbikeDTO motorbikeDTO) {
		Motorbike motorbike = new Motorbike();
		motorbike.setId(0);
		motorbike.setMake(motorbikeDTO.getMake());
		motorbike.setModel(motorbikeDTO.getModel());
		motorbike.setColor(motorbikeDTO.getColor());
		motorbike.setDateOfProduction(motorbikeDTO.getDateOfProduction());
		motorbike.setPrice(motorbikeDTO.getPrice());
		motorbike.setEngineType( new EngineType(motorbikeDTO.getEngineTypeId()) );
		motorbike.setAutoshowroom( new Autoshowroom(motorbikeDTO.getAutoshowroomId()) );
		motorbike.setBuyer( new Buyer(motorbikeDTO.getBuyerId()) );
		motorbike.setMotorbikeType( new MotorbikeType(motorbikeDTO.getMotorbikeTypeId()) );
		motorbike.setTyreSize(motorbikeDTO.getTyreSize());
		try {
			motorbikeService.saveMotorbike(motorbike);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return motorbike;
	}

	
	@DeleteMapping("/{motorbikeId}")
	public String deleteMotorbike(@PathVariable int motorbikeId) {
		// throw exception if motorbike doesn't exists
		Motorbike tempMotorbike = motorbikeService.getMotorbike(motorbikeId);
		if (tempMotorbike == null) {
			throw new EntityNotFoundException("Motorbike id not found - " + motorbikeId);
		}
		motorbikeService.deleteMotorbike(motorbikeId);
		return "Deleted motorbike with id - " + motorbikeId;
	}
	
}
