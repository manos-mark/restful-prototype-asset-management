package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Motorbike;

public interface MotorbikeService {

	void saveMotorbike(Motorbike motorbike);

	Motorbike getMotorbike(int theId);

	void deleteMotorbike(int motorbikeId);

	List<Motorbike> getMotorbikes(int autoshowroomId);

}
