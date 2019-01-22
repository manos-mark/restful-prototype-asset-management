package com.manos.prototype.service;

import java.util.List;

import com.manos.prototype.entity.Autoshowroom;

public interface AutoshowroomService {

	public List<Autoshowroom> getAutoshowrooms();

	public void saveAutoshowroom(Autoshowroom autoshowroom);

	public Autoshowroom getAutoshowroom(int theId);

	public void deleteAutoshowroom(int theId);

	public List<Autoshowroom> searchAutoshowrooms(String theSearchName);

//	public List<Car> getCars(int theId);

//	public List<Motorbike> getMotorbikes(int theId);
}
