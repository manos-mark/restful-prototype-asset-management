package com.manos.prototype.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/users.sql")
class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(userDao).isNotNull();
	}

	@Test
	@Transactional
	void findByUserEmail_success() {
		assertThatCode(() -> { 
			userDao.getUserByEmail("john@luv2code.com");
		})
		.doesNotThrowAnyException();
		
		User user = userDao.getUserByEmail("john@luv2code.com");
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo("john@luv2code.com");
		assertThat(user.getFirstName()).isEqualTo("John");
		assertThat(user.getLastName()).isEqualTo("Doe");
		assertThat(user.getId()).isNotNull();
		assertThat(user.getPassword()).isEqualTo("$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K");
		assertThat(user).hasNoNullFieldsOrProperties();
	}

	@Test
	@Transactional
	void findByUserEmail_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userDao.getUserByEmail("manos@luv2code.com"); 
		});
	}
	
	@Test
	@Transactional
	void saveUser_success() {
		User user = new User();
		user.setEmail("mail@mail.com");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setPassword("test");
		
		assertThatCode(() -> { 
			userDao.saveUser(user);
		}).doesNotThrowAnyException();
		
		User savedUser = userDao.getUserById(4);
		assertThat(savedUser.getFirstName()).isEqualTo("firstName");
		assertThat(savedUser.getLastName()).isEqualTo("lastName");
		assertThat(savedUser.getPassword()).isEqualTo("test");
		assertThat(savedUser.getEmail()).isEqualTo("mail@mail.com");
	}
	
	@Test
	@Transactional
	void saveUser_fail() {
		assertThatIllegalArgumentException()
		.isThrownBy(() -> {
			userDao.saveUser(null);
		});
	}
	
	@Test
	@Transactional
	void getUserDaoById_success() {
		User user = userDao.getUserByEmail("john@luv2code.com");
		assertThatCode(() -> { 
			userDao.getUserById(user.getId());
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void getUserDaoById_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userDao.getUserById(1000); 
		});
	}
	
	@Test
	@Transactional
	void deleteUser_success() {
		User user = userDao.getUserByEmail("john@luv2code.com");
		assertThatCode(() -> { 
			userDao.deleteUser(user.getId());
		}).doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void deleteUser_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userDao.deleteUser(1000); 
		});
	}
}















