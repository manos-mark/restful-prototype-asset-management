package com.manos.prototype.test.dao;

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

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;
import com.manos.prototype.test.config.AppConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts = "classpath:/sql/users.sql")
class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Test
	@Transactional
	void findByUserEmail_success() {
		assertThatCode(() -> { 
			userDao.findByUserEmail("john@luv2code.com");
		})
		.doesNotThrowAnyException();
		
		User user = userDao.findByUserEmail("john@luv2code.com");
//		assertThat(user).isNotNull();
//		assertThat(user.getEmail()).isEqualTo("john@luv2code.com");
//		assertThat(user.getFirstName()).isEqualTo("John");
//		assertThat(user.getLastName()).isEqualTo("Doe");
//		assertThat(user.getId()).isNotNull();
//		assertThat(user.getPassword()).isEqualTo("$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K");
		assertThat(user).hasNoNullFieldsOrProperties();
	}

	@Test
	@Transactional
	void findByUserEmail_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userDao.findByUserEmail("manos@luv2code.com"); 
		});
	}
	
	@Test
	@Transactional
	void saveUser_success() {
		User user = userDao.findByUserEmail("john@luv2code.com");
		user.setEmail("mail@mail.com");
		assertThatCode(() -> { 
			userDao.saveUser(user);
		})
		.doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void saveUser_fail() {
		User user = null;
		assertThatIllegalArgumentException()
		.isThrownBy(() -> {
			userDao.saveUser(user);
		});
	}
	
	@Test
	@Transactional
	void getUser_success() {
		User user = userDao.findByUserEmail("john@luv2code.com");
		assertThatCode(() -> { 
			userDao.getUser(user.getId());
		})
		.doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void getUser_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userDao.getUser(1000); 
		});
	}
	
	@Test
	@Transactional
	void deleteUser_success() {
		User user = userDao.findByUserEmail("john@luv2code.com");
		assertThatCode(() -> { 
			userDao.deleteUser(user.getId());
		})
		.doesNotThrowAnyException();
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















