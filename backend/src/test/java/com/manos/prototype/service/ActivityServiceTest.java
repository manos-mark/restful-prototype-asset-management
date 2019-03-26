package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.ActivityDaoImpl;
import com.manos.prototype.dao.UserDaoImpl;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.ActivityAction;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
	
	@Mock
	private ActivityDaoImpl activityDao;
	
	@InjectMocks
	private ActivityServiceImpl activityService;

	@Mock
	private UserDaoImpl userDao;
	
	@Mock
	private UserServiceImpl userService;
		
	@Test
	public void getActivities_getActivities_success() {
		List<Activity> mockActivities = createMockActivities();
		UserDetailsImpl mockUser = createMockUserDetails();

		when(userService.getCurrentUserDetails())
			.thenReturn(mockUser);
		when(activityDao.getActivitiesByUserId(mockUser.getId()))
			.thenReturn(mockActivities);
		
		List<Activity> activities = activityService.getActivities();
		
		assertThat(activities).isEqualTo(mockActivities);
		assertThat(activities.get(0))
			.isEqualToComparingFieldByFieldRecursively(mockActivities.get(0));
	}
	
	@Test
	public void saveActivity_nullUserId_fail() {
		Activity mockActivity = createMockActivity();
		User user = new User();
		user.setEmail("mail");
		user.setFirstName("firstName");
		user.setId(null);
		user.setLastName("lastName");
		user.setPassword("123");
		UserDetailsImpl mockUserDetails = new UserDetailsImpl(user);
		
		when(userService.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				activityService.saveActivity(mockActivity);
			});
	}
	
	@Test
	public void saveActivity_nullUser_fail() {
		Activity mockActivity = createMockActivity();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		
		when(userService.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				activityService.saveActivity(mockActivity);
			});
	}
	
	
	public Activity createMockActivity() {
		Activity activity = new Activity();
		activity.setAction(new ActivityAction(2, "NEW"));
		activity.setDate("2011-12-17 13:17:17");
		activity.setId(1L);
		activity.setUser(createMockUser());
		return activity;
	}
	
	public List<Activity> createMockActivities() {
		List<Activity> activities = new ArrayList<Activity>();
		activities.add(createMockActivity());
		return activities;
	}
	
	public UserDetailsImpl createMockUserDetails() {
		User user = new User();
		user.setEmail("mail");
		user.setFirstName("firstName");
		user.setId(1L);
		user.setLastName("lastName");
		user.setPassword("123");
		return new UserDetailsImpl(user);
	}
	
	public User createMockUser() {
		User user = new User();
		user.setEmail("mail");
		user.setFirstName("firstName");
		user.setId(1L);
		user.setLastName("lastName");
		user.setPassword("123");
		return user;
	}
}





