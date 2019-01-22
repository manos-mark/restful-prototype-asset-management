package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Car;

@Repository
public class CarDAOimpl implements CarDAO {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Car> getCars(int theId) {
		// get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        // create the query
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select distinct c FROM Car c ");
        queryBuilder.append("left join fetch c.engineType ");
        queryBuilder.append("left join fetch c.autoshowroom ");
        queryBuilder.append("left join fetch c.buyer ");
        queryBuilder.append("left join fetch c.carType ");
        queryBuilder.append("WHERE c.autoshowroom.id=:theId");
        
        Query<Car> theQuery = currentSession.createQuery(queryBuilder.toString());
        theQuery.setParameter("theId", theId);
		return theQuery.getResultList();
	}
	
	@Override
	public void saveCar(Car car) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		currentSession.saveOrUpdate(car);
	}

	@Override
	public Car getCar(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select distinct c FROM Car c ");
        queryBuilder.append("left join fetch c.engineType ");
        queryBuilder.append("left join fetch c.autoshowroom ");
        queryBuilder.append("left join fetch c.buyer ");
        queryBuilder.append("left join fetch c.carType ");
        queryBuilder.append("WHERE c.id=:theId");
        
        Query<Car> theQuery = currentSession.createQuery(queryBuilder.toString());
        theQuery.setParameter("theId", theId);
		return theQuery.getSingleResult();
	}

	@Override
	public void deleteCar(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		currentSession.delete(currentSession.get(Car.class,theId));
	}

	@Override
	public List<Car> getCars() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		Query<Car> theQuery = 
				currentSession.createQuery("from Car", Car.class);
		// execute query and get result
		return theQuery.getResultList();
	}

//	@Override
//	public List<Autoshowroom> searchAutoshowrooms(String theSearchName) {
//		 // get the current hibernate session
//        Session currentSession = sessionFactory.getCurrentSession();
//        
//        Query theQuery = null;
//        
//        //
//        // only search by name if theSearchName is not empty
//        //
//        if (theSearchName != null && theSearchName.trim().length() > 0) {
//
//            // search for firstName or lastName ... case insensitive
//            theQuery = currentSession.createQuery("from Autoshowroom where lower(name) like :theName", Autoshowroom.class);
//            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
//
//        }
//        else {
//            // theSearchName is empty ... so just get all customers
//            theQuery =currentSession.createQuery("from Autoshowroom", Autoshowroom.class);            
//        }
//        
//        // execute query and get result list
//        List<Autoshowroom> customers = theQuery.getResultList();
//                
//        // return the results        
//        return customers;
//        
//	}

}















