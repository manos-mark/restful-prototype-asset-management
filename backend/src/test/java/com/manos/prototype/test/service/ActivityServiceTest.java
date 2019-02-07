package com.manos.prototype.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.manos.prototype.dao.ActivityDao;
import com.manos.prototype.dao.UserDao;
import com.manos.prototype.dto.UserDto;
import com.manos.prototype.entity.Activity;
import com.manos.prototype.entity.ActivityAction;
import com.manos.prototype.entity.User;
import com.manos.prototype.service.ActivityServiceImpl;
import com.manos.prototype.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
	
	List<Activity> activities = new ArrayList<Activity>();
	Activity activityOne = new Activity();
	
	@Mock
	private ActivityDao activityDao;
	
	@Mock
	private UserDao userDaoMock;

	@InjectMocks
	private ActivityServiceImpl activityService;
	
	@Mock
	private UserServiceImpl userService;
		
	@Before
	public void init() {
		activityOne.setAction(new ActivityAction(2, "NEW"));
		activityOne.setDate("2011-12-17 13:17:17");
		activityOne.setId(1L);
		activityOne.setUser(new User("test","test", "test", "test"));
		activities.add(activityOne);
	}
	
	@Test
	public void getActivities_getCurrentUser() {
		UserDto userDto = new UserDto();
		userDto.setEmail("mail");
		userDto.setFirstName("firstName");
		userDto.setId(1L);
		userDto.setLastName("lastName");
		
		when(userService.getCurrentUserDto())
			.thenReturn(userDto);
		
		assertThat(userService.getCurrentUserDto().getId())
			.isEqualTo(1);
		assertThat(userService.getCurrentUserDto().getFirstName())
		.isEqualTo("firstName");
		assertThat(userService.getCurrentUserDto().getLastName())
		.isEqualTo("lastName");
		assertThat(userService.getCurrentUserDto().getEmail())
			.isEqualTo("mail");
		assertThatCode(() -> { 
			userService.getCurrentUserDto();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getActivities_getActivitiesByUserId() {
		when(activityDao.getActivitiesByUserId(1))
			.thenReturn(activities);
		
		assertThat(activityDao.getActivitiesByUserId(1))
			.size().isEqualTo(1);
		assertThatCode(() -> { 
			activityDao.getActivitiesByUserId(1);
		}).doesNotThrowAnyException();
		
		verify(activityDao, times(2)).getActivitiesByUserId(1);
	}
	
	@Test
	public void saveActivity() {
//		assertThatCode(() -> { 
//			activityDao.saveActivity(activityOne);
//		}).doesNotThrowAnyException();
		activityDao.saveActivity(activityOne);
		verify(activityDao, times(1)).saveActivity(activityOne);
	}

}





