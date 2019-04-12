package com.manos.prototype.finder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.manos.prototype.config.AppConfigUnitTest;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.finder.ActivityFinder;
import com.manos.prototype.finder.UserFinder;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfigUnitTest.class })
@Sql(scripts = "classpath:/sql/actions.sql")
@Sql(scripts = "classpath:/sql/users.sql")
@Sql(scripts = "classpath:/sql/activities.sql")
public class ActivityFinderTest {

	@Autowired
	private ActivityFinder activityFinder;
	
	@Autowired
	private UserFinder userDao;
	
	@Test
	@Transactional
	public void autowiredDao_success() {
		assertThat(activityFinder).isNotNull();
		assertThat(userDao).isNotNull();
	}

	@Test
	@Transactional
	public void getActivitiesByUserId_success() {
		List<Activity> userOneActivities = activityFinder.getActivitiesByUserId(1);
		assertThat(userOneActivities).size().isEqualTo(2);
		
		List<Activity> userTwoActivities = activityFinder.getActivitiesByUserId(2);
		assertThat(userTwoActivities).size().isEqualTo(1);
	}
	
	@Test
	@Transactional
	public void getActivitiesByUserId_fail() {
		List<Activity> activities = activityFinder.getActivitiesByUserId(100);
		assertThat(activities).isEmpty();
	}
	
}











