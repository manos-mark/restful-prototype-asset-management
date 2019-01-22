package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.AutoshowroomDAO;
import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Car;
import com.manos.prototype.entity.Motorbike;

@Service
public class AutoshowroomServiceImpl implements AutoshowroomService {
	
	// need to inject the dao
	@Autowired
	private AutoshowroomDAO autoshowroomDAO;
		
	@Override
	@Transactional
	public List<Autoshowroom> getAutoshowrooms() {
		return autoshowroomDAO.getAutoshowrooms();
	}

	@Override
	@Transactional
	public void saveAutoshowroom(Autoshowroom autoshowroom) {
		autoshowroomDAO.saveAutoshowroom(autoshowroom);
	}

	@Override
	@Transactional
	public Autoshowroom getAutoshowroom(int theId) {
		return autoshowroomDAO.getAutoshowroom(theId);
	}

	@Override
	@Transactional
	public void deleteAutoshowroom(int theId) {
		autoshowroomDAO.deleteAutoshowroom(theId);
	}

	@Override
	@Transactional
	public List<Autoshowroom> searchAutoshowrooms(String theSearchName) {
		return autoshowroomDAO.searchAutoshowrooms(theSearchName);
	}

//	@Override
//	@Transactional
//	public List<Car> getCars(int theId) {
//		return autoshowroomDAO.getCars(theId);
//	}

//	@Override
//	@Transactional
//	public List<Motorbike> getMotorbikes(int theId) {
//		return autoshowroomDAO.getMotorbikes(theId);
//	}

}
