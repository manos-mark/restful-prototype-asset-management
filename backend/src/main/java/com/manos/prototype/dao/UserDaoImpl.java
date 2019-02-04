package com.manos.prototype.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	
	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;

	
	@Override
	public User findByUserEmail(String theEmail) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using username
		Query<User> theQuery = currentSession.createQuery("from User where email=:mail", User.class);
		theQuery.setParameter("mail", theEmail);
		return theQuery.getSingleResult();
	}
	
	@Override
	public void saveUser(User theUser) {
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(theUser);
	}
	
	@Override
	public User getUser(long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
//		queryBuilder.append("from User u left join fetch u.roles where u.id=:userId");
		queryBuilder.append("from User u where u.id=:userId");
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
