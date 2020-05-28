package com.manos.prototype.finder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.User;
import com.manos.prototype.finder.UserFinder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/users.sql")
class UserFinderTest {

	@Autowired
	private UserFinder userFinder;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(userFinder).isNotNull();
	}

	@Test
	@Transactional
	void findByUserEmail_success() {
		assertThatCode(() -> { 
			userFinder.getUserByEmail("john@luv2code.com");
		})
		.doesNotThrowAnyException();
		
		User user = userFinder.getUserByEmail("john@luv2code.com");
		assertThat(user).isNotNull();
		assertThat(user.getEmail()).isEqualTo("john@luv2code.com");
		assertThat(user.getFirstName()).isEqualTo("John");
		assertThat(user.getLastName()).isEqualTo("Doe");
		assertThat(user.getId()).isNotNull();
		assertThat(user.getPassword()).isEqualTo("$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K");
	}

	@Test
	@Transactional
	void findByUserEmail_fail() {
		assertThatExceptionOfType(NoResultException.class)
		.isThrownBy(() -> { 
			userFinder.getUserByEmail("manos@luv2code.com"); 
		});
	}
	
}















