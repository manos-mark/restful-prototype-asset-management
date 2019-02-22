package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Project;
import com.manos.prototype.search.ProjectSearch;
import com.pastelstudios.db.PagingAndSortingSupport;
import com.pastelstudios.db.SearchSupport;
import com.pastelstudios.paging.PageRequest;

@Repository
public class ProjectDaoImpl {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PagingAndSortingSupport pagingSupport;
	
	@Autowired
	private SearchSupport searchSupport;
	
	public List<Project> getProjects(PageRequest pageRequest, ProjectSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project p ")
					.append("join fetch p.projectManager ")
					.append("join fetch p.status pStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		queryString = pagingSupport.applySorting(queryString, pageRequest);

		return pagingSupport
				.applyPaging(currentSession.createQuery(queryString, Project.class), pageRequest)
				.setProperties(search)
				.getResultList();
	}
	
	public List<Project> getProjects() {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project p ");
		
		return currentSession.createQuery(queryBuilder.toString(), Project.class).getResultList();
	}

	public Project getProject(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project p ")
					.append("join fetch p.projectManager ")
					.append("join fetch p.status ")
					.append("where p.id = :id");
		Query<Project> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Project.class);
		theQuery.setParameter("id", id);
		
		return theQuery.getSingleResult();
	}

	public void deleteProject(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Project> theQuery = currentSession
				.createQuery("from Project p where p.id = :id", Project.class);
		theQuery.setParameter("id", id);
		
		Project project = theQuery.getSingleResult();
		currentSession.delete(project);	
	}

	public void saveProject(Project project) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.save(project);
	}
	
	public void updateProject(Project project) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.update(project);
	}

	public Long getProjectsCountByStatus(int statusId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Project p "
				+ "inner join p.status s "
				+ "where s.id = :statusId");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("statusId", statusId);
		
		return theQuery.getSingleResult();
	}

	public Long count() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Project p ");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		
		return theQuery.getSingleResult();
	}
	
	public Long count(ProjectSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Project p ")
					.append("join p.status pStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		Query<Long> theQuery = currentSession.createQuery(queryString, Long.class);
		theQuery.setParameter("statusId", search.getStatusId());
		return theQuery.getSingleResult();
	}

}
