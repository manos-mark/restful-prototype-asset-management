package com.manos.prototype.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.User;

@Repository
public class UserDaoImpl {

	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	
	public User getUserByEmail(String theEmail) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where email=:mail", User.class);
		theQuery.setParameter("mail", theEmail);
		return theQuery.getSingleResult();
	}
}
