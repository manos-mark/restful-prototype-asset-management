package com.manos.prototype.test.dao;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;
import com.manos.prototype.test.config.AppConfigTest;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts="/backend/src/test/resources/sql/users.sql")
class UserDaoImplTest {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	@Transactional
	void findByUserEmail() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> theQuery = currentSession.createQuery("from User where email=:mail", User.class);
		theQuery.setParameter("mail", "john@luv2code.com");
		
//		when(theQuery.getSingleResult()).thenReturn(User.class);
	}

	@Test
	@Transactional
	void findByUserEmail_found() {
//		Session currentSession = sessionFactory.getCurrentSession();
		User user = userDao.findByUserEmail("john@luv2code.com");
		when(userDao.findByUserEmail("john@luv2code.com")).thenReturn(user);
		
		userDao.findByUserEmail("john@luv2code.com");
	}
	
	@Test
	@Transactional
	void findByUserEmail_notFound() {
//		Session currentSession = sessionFactory.getCurrentSession();
		doThrow(new EntityNotFoundException()).when(userDao.findByUserEmail(anyString()));
		
		userDao.findByUserEmail("john@luv2code.com");
	}
}
