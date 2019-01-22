package com.manos.prototype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.MotorbikeDAO;
import com.manos.prototype.entity.Motorbike;
@Service
public class MotorbikeServiceImpl implements MotorbikeService {
	
	// need to inject the dao
	@Autowired
	private MotorbikeDAO motorbikeDAO;

	@Override
	@Transactional
	public void saveMotorbike(Motorbike motorbike) {
		motorbikeDAO.saveMotorbike(motorbike);
	}

	@Override
	@Transactional
	public Motorbike getMotorbike(int theId) {
		return motorbikeDAO.getMotorbike(theId);
	}

	@Override
	@Transactional
	public void deleteMotorbike(int theId) {
		motorbikeDAO.deleteMotorbike(theId);
	}

//	@Override
//	@Transactional
//	public List<Autoshowroom> searchAutoshowrooms(String theSearchName) {
//		return autoshowroomDAO.searchAutoshowrooms(theSearchName);
//	}
//
	@Override
	@Transactional
	public List<Motorbike> getMotorbikes(int theId) {
		return motorbikeDAO.getMotorbikes(theId);
	}

}
