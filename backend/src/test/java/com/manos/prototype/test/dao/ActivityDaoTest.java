package com.manos.prototype.test.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.test.config.AppConfigTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigTest.class })
@Sql(scripts = "classpath:/sql/actions.sql")
@Sql(scripts = "classpath:/sql/users.sql")
@Sql(scripts = "classpath:/sql/activities.sql")
public class ActivityDaoTest {

	@Autowired
	private ActivityDao activityDao;

	@Test
	@Transactional
	void getActivities_success() {
		List<Activity> userOneActivities = activityDao.getActivities(1);
		assertThat(userOneActivities).size().isEqualTo(2);
		
		List<Activity> userTwoActivities = activityDao.getActivities(2);
		assertThat(userTwoActivities).size().isEqualTo(1);
	}
	
	@Test
	@Transactional
	void getActivities_fail() {
		List<Activity> activities = activityDao.getActivities(100);
		assertThat(activities).isEmpty();
	}
	
	@Test
	@Transactional
	void addActivity_success() {
		List<Activity> userTwoActivities = activityDao.getActivities(2);
		assertThat(userTwoActivities).size().isEqualTo(1);
		
		Activity activity = userTwoActivities.get(0);
		activity.setDate("2019-12-17 14:14:14");
		
		assertThatCode(() -> { 
			activityDao.addActivity(activity);
		})
		.doesNotThrowAnyException();
	}
	
	@Test
	@Transactional
	void addActivity_fail() {
		assertThatExceptionOfType(IllegalArgumentException.class)
		.isThrownBy(() -> { 
			activityDao.addActivity(null); 
		});
	}
}











