package com.manos.prototype.dao;

import java.util.List;

import com.manos.prototype.entity.Autoshowroom;
import com.manos.prototype.entity.Car;
import com.manos.prototype.entity.Motorbike;

public interface AutoshowroomDAO {
	
	public List<Autoshowroom> getAutoshowrooms();

	public void saveAutoshowroom(Autoshowroom autoshowroom);

	public Autoshowroom getAutoshowroom(int theId);

	public void deleteAutoshowroom(int theId);

	public List<Autoshowroom> searchAutoshowrooms(String theSearchName);

//	public List<Car> getCars(int theId);

//	public List<Motorbike> getMotorbikes(int theId);
}
