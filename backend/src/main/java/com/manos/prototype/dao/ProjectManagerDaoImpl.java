package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.ProjectManager;

@Repository
public class ProjectManagerDaoImpl {

	@Autowired
    private SessionFactory sessionFactory;
	
	public ProjectManager getProjectManager(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from ProjectManager pm ")
					.append("where pm.id = :id");
		
		Query<ProjectManager> theQuery = currentSession.createQuery(queryBuilder.toString(), ProjectManager.class);
		theQuery.setParameter("id", id);
		return theQuery.getSingleResult();
	}
	
	public List<ProjectManager> getProjectManagers() {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from ProjectManager pm ");
		Query<ProjectManager> theQuery = currentSession.createQuery(queryBuilder.toString(), ProjectManager.class);
		return theQuery.getResultList();
	}
}
