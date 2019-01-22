package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Autoshowroom;

@Repository
public class AutoshowroomDAOimpl implements AutoshowroomDAO {
	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Autoshowroom> getAutoshowrooms() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		Query<Autoshowroom> theQuery = 
				currentSession.createQuery("from Autoshowroom order by name", Autoshowroom.class);
		// execute query and get result
		List<Autoshowroom> autoshowrooms = theQuery.getResultList();
		return autoshowrooms;
	}

	@Override
	public void saveAutoshowroom(Autoshowroom autoshowroom) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		currentSession.saveOrUpdate(autoshowroom);
	}

	@Override
	public Autoshowroom getAutoshowroom(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
		return currentSession.get(Autoshowroom.class, theId);
	}

	@Override
	public void deleteAutoshowroom(int theId) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query
//		Query theQuery = currentSession.createQuery("delete from Autoshowroom where id =: autoshowroomId");
//		theQuery.setParameter("autoshowroomId", theId);
//		theQuery.executeUpdate();
		currentSession.delete(currentSession.get(Autoshowroom.class,theId));
	}

	@Override
	public List<Autoshowroom> searchAutoshowrooms(String theSearchName) {
		 // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery = currentSession.createQuery("from Autoshowroom where lower(name) like :theName", Autoshowroom.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Autoshowroom", Autoshowroom.class);            
        }
        
        // execute query and get result list
        List<Autoshowroom> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
        
	}

//	@Override
//	public List<Car> getCars(int theId) {
//		// get the current hibernate session
//        Session currentSession = sessionFactory.getCurrentSession();
//        // create the query
//        Query<Car> theQuery = currentSession.createQuery("FROM Car c WHERE c.autoshowroom.id=:theId");
//        theQuery.setParameter("theId", theId);
//		return theQuery.getResultList();
//	}
	
//	@Override
//	public List<Motorbike> getMotorbikes(int theId) {
//		// get the current hibernate session
//        Session currentSession = sessionFactory.getCurrentSession();
//        // create the query
//        Query<Motorbike> theQuery = currentSession.createQuery("FROM Motorbike c WHERE c.autoshowroom.id=:theId");
//        theQuery.setParameter("theId", theId);
//        System.out.println();
//        System.out.println(theQuery.getResultList());
//        System.out.println();
//		return theQuery.getResultList();
//	}
}















