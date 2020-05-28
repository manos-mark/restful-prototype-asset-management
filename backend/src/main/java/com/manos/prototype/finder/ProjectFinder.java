package com.manos.prototype.finder;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.manos.prototype.entity.Project;
import com.manos.prototype.search.ProjectSearch;
import com.manos.prototype.vo.ProjectVo;
import com.pastelstudios.db.AbstractFinder;
import com.pastelstudios.db.PagingAndSortingSupport;
import com.pastelstudios.db.SearchSupport;
import com.pastelstudios.paging.PageRequest;

@Repository
public class ProjectFinder extends AbstractFinder {

	@Autowired
	private PagingAndSortingSupport pagingSupport;
	
	@Autowired
	private SearchSupport searchSupport;
	
	public List<ProjectVo> getProjects(PageRequest pageRequest, ProjectSearch search) {
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
				.applyPaging(createQuery(queryString, ProjectVo.class), pageRequest)
				.setProperties(search)
				.getResultList();
	}
	
	public Long getProjectsCountByStatus(int statusId) {
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(p.id) from Project p ")
					.append("inner join p.status s ")
					.append("where s.id = :statusId");
		
		Query<Long> theQuery = createQuery(queryBuilder.toString(), Long.class);
		theQuery.setParameter("statusId", statusId);
		
		return theQuery.getSingleResult();
	}

	public int count(ProjectSearch search) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select count(project.id) as projectsCount")
					.append(" from Project project ")
					.append(" join project.status projectStatus ");
		String queryString = searchSupport.addSearchConstraints(queryBuilder.toString(), search);
		
		Long count = createQuery(queryString, Long.class)
				.setProperties(search)
				.getSingleResult();
		return count == null ? 0 : count.intValue();
	}
	
	public List<Project> search(String text, String field) {
		FullTextSession fullTextSession = Search.getFullTextSession(this.getSession());
		// Using an Hibernate Session to rebuild an index
//		try {
//			fullTextSession.createIndexer().startAndWait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		QueryBuilder qb = fullTextSession
								.getSearchFactory()
								.buildQueryBuilder()
								.forEntity(Project.class)
								.overridesForField(field, "my_analyzer_without_ngrams")
								.get();
		
		org.apache.lucene.search.Query lucenceQuery 
			= qb.keyword()
				.onFields(field)
				.matching(text)
				.createQuery();
		
		return fullTextSession.createFullTextQuery(lucenceQuery, Project.class).getResultList();
	}

	public Project getProjectByName(String projectName) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("from Project project ")
					.append("where project.projectName = :projectName");
		
		Query<Project> query = createQuery(queryBuilder.toString(), Project.class);
		query.setParameter("projectName", projectName);
		
		return query.uniqueResult();
	}
}
