package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Motorbike;

@Repository
public class MotorbikeDAOimpl implements MotorbikeDAO {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveMotorbike(Motorbike motorbike) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		currentSession.saveOrUpdate(motorbike);
	}

	@Override
	public Motorbike getMotorbike(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select distinct m FROM Motorbike m ");
        queryBuilder.append("left join fetch m.engineType ");
        queryBuilder.append("left join fetch m.autoshowroom ");
        queryBuilder.append("left join fetch m.buyer ");
        queryBuilder.append("left join fetch m.motorbikeType ");
        queryBuilder.append("WHERE m.id=:theId");
        
        Query<Motorbike> theQuery = currentSession.createQuery(queryBuilder.toString());
        theQuery.setParameter("theId", theId);
        
		return theQuery.getSingleResult();
	}

	@Override
	public void deleteMotorbike(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		currentSession.delete(currentSession.get(Motorbike.class,theId));
//		currentSession.delete(currentSession.get(Vehicle.class,theId));
	}

	@Override
	public List<Motorbike> getMotorbikes(int theId) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select distinct m FROM Motorbike m ");
        queryBuilder.append("left join fetch m.engineType ");
        queryBuilder.append("left join fetch m.autoshowroom ");
        queryBuilder.append("left join fetch m.buyer ");
        queryBuilder.append("left join fetch m.motorbikeType ");
        queryBuilder.append("WHERE m.autoshowroom.id=:theId");
        System.out.println("test1");
        Query<Motorbike> theQuery = currentSession.createQuery(queryBuilder.toString());
        theQuery.setParameter("theId", theId);
        
        System.out.println("test2");
		return theQuery.getResultList();
	}

}















