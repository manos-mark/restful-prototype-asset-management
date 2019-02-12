package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.ActivityAction;
import com.manos.prototype.entity.User;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.service.ActivityServiceImpl;
import com.manos.prototype.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
	
	@Mock
	private ActivityDao activityDao;
	
	@Mock
	private UserDao userDao;

	@InjectMocks
	private ActivityServiceImpl activityService;
	
	@Mock
	private UserServiceImpl userService;
		
	@Test
	public void getActivities_getCurrentUserDetails_nullUserFail() {
		when(userService.getCurrentUserDetails())
			.thenReturn(null);
		
		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> {
			activityService.getActivities();
		});
	}
	
	@Test
	public void getActivities_getActivitiesByUserId_success() {
		List<Activity> mockActivities = createMockActivities();
		UserDetailsImpl mockUser = createMockUserDetails();

		when(userService.getCurrentUserDetails())
			.thenReturn(mockUser);
		when(activityService.getActivities())
			.thenReturn(mockActivities);
		
		UserDetailsImpl user = userService.getCurrentUserDetails();
		List<Activity> activities = activityService.getActivities();
		
		assertThat(user).isEqualTo(mockUser);
		assertThat(user)
			.isEqualToComparingFieldByFieldRecursively(mockUser);
		assertThat(activities).isEqualTo(mockActivities);
		assertThat(activities.get(0))
			.isEqualToComparingFieldByFieldRecursively(mockActivities.get(0));
	}
	
	@Test
	public void saveActivity_success() {
		Activity mockActivity = createMockActivity();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		User mockUser = createMockUser();

		when(userService.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(userService.getUser(1))
			.thenReturn(mockUser);
		
		UserDetailsImpl userDetails = userService.getCurrentUserDetails();
		User user = userService.getUser(1);
		
		assertThat(userDetails).isEqualTo(mockUserDetails);
		assertThat(userDetails)
			.isEqualToComparingFieldByFieldRecursively(mockUserDetails);
		assertThat(user).isEqualTo(mockUser);
		assertThat(user)
			.isEqualToComparingFieldByFieldRecursively(mockUser);
		assertThatCode(() -> { 
			activityService.saveActivity(mockActivity);
		}).doesNotThrowAnyException();
	}

	@Test
	public void saveActivity_nullUserFail() {
		Activity mockActivity = createMockActivity();
		mockActivity.setUser(null);
		
		assertThatExceptionOfType(NullPointerException.class)
		.isThrownBy(() -> {
			activityService.saveActivity(mockActivity);
		});
	}
	
	
	public Activity createMockActivity() {
		Activity activity = new Activity();
		activity.setAction(new ActivityAction(2, "NEW"));
		activity.setDate("2011-12-17 13:17:17");
		activity.setId(1L);
		activity.setUser(new User("test","test", "test", "test"));
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





