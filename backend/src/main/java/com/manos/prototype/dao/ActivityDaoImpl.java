package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Activity;

@Repository
public class ActivityDaoImpl implements ActivityDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Activity> getActivities(long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Activity a "
				+ "left join fetch a.action "
				+ "left join fetch a.user "
				+ "where a.user.id = :userId ");
		Query<Activity> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Activity.class);
		theQuery.setParameter("userId", userId);
		return theQuery.getResultList();
	}

	@Override
	public void addActivity(Activity activity) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(activity);
	}
}
