package com.manos.prototype.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Project;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
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
	
	public List<ProjectVo> getProjects(PageRequest pageRequest, ProjectSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select new com.manos.prototype.vo.ProjectVo(project, count(product.id) as productsCount) ")
					.append(" from Project project ")
					.append(" left join project.products product ")
					.append(" join project.projectManager projectManager ")
					.append(" join project.status projectStatus ");
					
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		queryString += " group by project.id ";
		queryString = pagingSupport.applySorting(queryString, pageRequest);

		return pagingSupport
				.applyPaging(currentSession.createQuery(queryString, ProjectVo.class), pageRequest)
				.setProperties(search)
				.getResultList();
	}
	
	public List<Project> getProjects() {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project project ");
		
		return currentSession.createQuery(queryBuilder.toString(), Project.class).getResultList();
	}

	public Project getProject(int id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project project ")
					.append("join fetch project.projectManager ")
					.append("join fetch project.status ")
					.append("where project.id = :id");
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
		queryBuilder.append("select count(p.id) from Project p ")
					.append("inner join p.status s ")
					.append("where s.id = :statusId");
		
		Query<Long> theQuery = currentSession
				.createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("statusId", statusId);
		
		return theQuery.getSingleResult();
	}

	public int count(ProjectSearch search) {
		Session currentSession = sessionFactory.getCurrentSession();
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(project.id) as projectsCount")
					.append(" from Project project ")
					.append(" join project.status projectStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		
		Long count = currentSession.createQuery(queryString, Long.class)
				.setProperties(search)
				.getSingleResult();
		return count == null ? 0 : count.intValue();
	}

}
