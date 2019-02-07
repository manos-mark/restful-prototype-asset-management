package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Project;

@Repository
public class ProjectDaoImpl implements ProjectDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Project> getProjects() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Project> theQuery = currentSession
				.createQuery("from Project", Project.class);
		return theQuery.getResultList();
	}

	@Override
	public Project getProject(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Project> theQuery = currentSession
				.createQuery("from Project p where p.id = :id", Project.class);
		theQuery.setParameter("id", id);
		return theQuery.getSingleResult();
	}

	@Override
	public void deleteProject(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Project> theQuery = currentSession
				.createQuery("from Project p where p.id = :id", Project.class);
		theQuery.setParameter("id", id);
		Project project = theQuery.getSingleResult();
		currentSession.delete(project);	
	}

	@Override
	public void saveProject(Project project) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(project);
	}

}
