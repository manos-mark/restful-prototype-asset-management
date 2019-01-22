package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Motorbike;

public interface MotorbikeDAO {
	
	public void saveMotorbike(Motorbike motorbike);

	public Motorbike getMotorbike(int theId);

	public void deleteMotorbike(int theId);

	List<Motorbike> getMotorbikes(int theId);

}
