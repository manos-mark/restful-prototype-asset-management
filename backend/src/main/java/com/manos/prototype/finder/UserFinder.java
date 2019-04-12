package com.manos.prototype.finder;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.User;
import com.pastelstudios.db.AbstractFinder;

@Repository
public class UserFinder extends AbstractFinder {

	public User getUserByEmail(String theEmail) {
		Query<User> theQuery = createQuery("from User where email=:mail", User.class);
		theQuery.setParameter("mail", theEmail);
		return theQuery.getSingleResult();
	}
}
