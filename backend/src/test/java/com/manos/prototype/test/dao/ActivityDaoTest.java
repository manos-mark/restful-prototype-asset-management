package com.manos.prototype.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.ActivityAction;
import com.manos.prototype.entity.User;
import com.manos.prototype.test.config.AppConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts = "classpath:/sql/actions.sql")
@Sql(scripts = "classpath:/sql/users.sql")
@Sql(scripts = "classpath:/sql/activities.sql")
public class ActivityDaoTest {

	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	@Transactional
	void autowiredDao_success() {
		assertThat(activityDao).isNotNull();
		assertThat(userDao).isNotNull();
	}

	@Test
	@Transactional
	void getActivitiesByUserId_success() {
		List<Activity> userOneActivities = activityDao.getActivitiesByUserId(1);
		assertThat(userOneActivities).size().isEqualTo(2);
		
		List<Activity> userTwoActivities = activityDao.getActivitiesByUserId(2);
		assertThat(userTwoActivities).size().isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getActivitiesByUserId_fail() {
		List<Activity> activities = activityDao.getActivitiesByUserId(100);
		assertThat(activities).isEmpty();
	}
	
	@Test
	@Transactional
	void saveActivity_success() {
		User user = userDao.getUserById(1); 
		
		ActivityAction action = new ActivityAction();
		action.setId(1);
		
		Activity activity = new Activity();
		activity.setDate("2019-12-17 14:14:14");
		activity.setAction(action);
		activity.setUser(user);
		
		assertThatCode(() -> { 
			activityDao.saveActivity(activity);
		}).doesNotThrowAnyException();
		
		Activity savedActivity = activityDao.getActivitiesByUserId(1).get(0);
		assertThat(savedActivity).isNotNull();
		assertThat(savedActivity.getAction()
				.getId()).isEqualTo(1);
		assertThat(savedActivity.getDate()).isEqualTo("2019-12-17 14:14:14");
		assertThat(savedActivity.getId()).isEqualTo(4);
		assertThat(savedActivity.getUser().getFirstName()).isEqualTo("John");
	}
	
	@Test
	@Transactional
	void saveActivity_fail() {
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> { 
			activityDao.saveActivity(null); 
		});
	}
}











