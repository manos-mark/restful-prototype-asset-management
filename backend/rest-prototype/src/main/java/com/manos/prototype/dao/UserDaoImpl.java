package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Car;
import com.manos.prototype.entity.Role;
import com.manos.prototype.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public User findByUserName(String theUserName) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
		theQuery.setParameter("uName", theUserName);
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	
	@Override
	public void saveUser(User theUser) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
//		currentSession.saveOrUpdate(theUser);
		currentSession.save(theUser);
	}

	
	@Override
	public List<User> getUsers() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select distinct u from User u left join fetch u.roles");
		
		Query<User> theQuery = currentSession.createQuery(queryBuilder.toString(), User.class);
		return theQuery.getResultList();
	}

	
	@Override
	public User getUser(long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from User u left join fetch u.roles where u.id=:userId");
		
		Query<User> theQuery = currentSession.createQuery(queryBuilder.toString(), User.class);
		theQuery.setParameter("userId", userId);
		
		return theQuery.getSingleResult();
	}


	@Override
	public void updateUser(User user) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(user);
	}


	@Override
	public void deleteUser(long userId) {
		Session currentSession = sessionFactory.getCurrentSession();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("delete User u where u.id=:userId");
		
		Query<?> theQuery = currentSession.createQuery(queryBuilder.toString());
		theQuery.setParameter("userId", userId);
		theQuery.executeUpdate();
	}

}
