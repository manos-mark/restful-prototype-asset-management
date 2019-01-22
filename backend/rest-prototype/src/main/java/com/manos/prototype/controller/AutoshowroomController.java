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

import com.manos.prototype.dto.AutoshowroomDTO;
import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Car;
import com.manos.prototype.entity.Motorbike;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.service.AutoshowroomService;

@RestController
@RequestMapping("/autoshowrooms")
public class AutoshowroomController {
	
	
	// need to inject the service
	@Autowired
	private AutoshowroomService autoshowroomService;
	
	@GetMapping("/{autoshowroomId}/*") 
	public void throwError() {
		throw new EntityNotFoundException("Sub-category not found (You should try /cars or /motorbikes)");
	}
	
	@GetMapping
	public List<Autoshowroom> getAutoshowrooms() {
		// get autoshowrooms from the dao
		List<Autoshowroom> autoshowrooms = autoshowroomService.getAutoshowrooms();
		// add them to the model
		return autoshowrooms;
	}

	
	@GetMapping("/{autoshowroomId}")
	public Autoshowroom getAutoshowroom(@PathVariable int autoshowroomId) {
		
		// get autoshowroom from the dao
		Autoshowroom autoshowroom = autoshowroomService.getAutoshowroom(autoshowroomId);
		
		if (autoshowroom == null) {
			throw new EntityNotFoundException("Autoshowroom id not found - " + autoshowroomId);
		}
		return autoshowroom;
	}
	
	
	@PutMapping
	public Autoshowroom updateAutoshowroom(@RequestBody AutoshowroomDTO autoshowroomDTO) {
		// throw exception if autoshowroom doesn't exists
		Autoshowroom tempAutoshowroom = autoshowroomService.getAutoshowroom(autoshowroomDTO.getId());
		if (tempAutoshowroom == null) {
			throw new EntityNotFoundException("Autoshowroom id not found - " + autoshowroomDTO.getId());
		}
		Autoshowroom autoshowroom = new Autoshowroom();
		autoshowroom.setId(autoshowroomDTO.getId());
		autoshowroom.setName(autoshowroomDTO.getName());
		try {
			autoshowroomService.saveAutoshowroom(autoshowroom);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return autoshowroom;
	}

	
	@PostMapping
	public Autoshowroom addAutoshowroom(@RequestBody AutoshowroomDTO autoshowroomDTO) {
		Autoshowroom autoshowroom = new Autoshowroom();
		autoshowroom.setName(autoshowroomDTO.getName());
		// saveOrUpdate -- if primary key is zero then insert else update
		autoshowroom.setId(0);
		try {
			autoshowroomService.saveAutoshowroom(autoshowroom);
		} catch (Exception e) {
			throw new EntityNotFoundException(e.getCause().getLocalizedMessage());
		}
		return autoshowroom;
	}
	
	
	@DeleteMapping("/{autoshowroomId}")
	public String deleteAutoshowroom(@PathVariable int autoshowroomId) {
		// throw exception if autoshowroom doesn't exists
		Autoshowroom tempAutoshowroom = autoshowroomService.getAutoshowroom(autoshowroomId);
		if (tempAutoshowroom == null) {
			throw new EntityNotFoundException("Autoshowroom id not found - " + autoshowroomId);
		}
		autoshowroomService.deleteAutoshowroom(autoshowroomId);
		return "Deleted autoshowroom with id - " + autoshowroomId;
	}

}
