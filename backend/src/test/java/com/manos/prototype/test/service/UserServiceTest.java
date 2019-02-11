package com.manos.prototype.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.service.UserServiceImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void getCurrentUserDetails() {
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		
		UserDetailsImpl userDetails = userService.getCurrentUserDetails();
		
		assertThat(userDetails).isEqualTo(mockUserDetails);
		assertThat(userDetails)
			.isEqualToComparingFieldByFieldRecursively(mockUserDetails);
		verify(userService, times(1)).getCurrentUserDetails();
	}
	
	@Test
	public void getCurrentUserDetails_nullUserFail() {
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.getCurrentUserDetails();
		});
	}
	
	@Test 
	public void findByEmail_success() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		
		User user = userService.findByEmail("mail");
		
		assertThat(user).isEqualTo(mockUser);
		assertThat(user).isEqualToComparingFieldByFieldRecursively(mockUser);
		verify(userService, times(1)).findByEmail("mail");
	}
	
	@Test 
	public void findByEmail_nullEmailFail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.findByEmail(null);
		});
	}
	
	@Test
	public void saveUser_success() {
		User mockUser = createMockUser();
		
		when(userService.findByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			userService.saveUser(mockUser);
		}).doesNotThrowAnyException();
		verify(userService, times(1)).saveUser(mockUser);
	}
	
	@Test
	public void saveUser_nullUserFail() {
		User mockUser = createMockUser();
		
		when(userService.findByEmail("mail"))
			.thenReturn(null);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.saveUser(mockUser);
		});
	}
	
	@Test
	public void saveUser_nullEncoderFail() {
		User mockUser = createMockUser();
		
		when(userService.findByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.saveUser(mockUser);
		});
	}
	
	@Test
	public void getUser_success() {
		User mockUser = createMockUser();
		
		when(userDao.getUserById(1))
			.thenReturn(mockUser);
		
		User user = userService.getUser(1);
		
		assertThat(user).isEqualTo(mockUser);
		assertThat(user).isEqualToComparingFieldByFieldRecursively(mockUser);
		verify(userService, times(1)).getUser(1);
	}
	
	@Test
	public void deleteUser_success() {
		User mockUser = createMockUser();
		
		when(userService.getUser(1))
			.thenReturn(mockUser);
		
		User user = userService.getUser(1);
		assertThat(user).isEqualTo(mockUser);
		assertThat(user).isEqualToComparingFieldByFieldRecursively(mockUser);
		assertThatCode(() -> { 
			userService.deleteUser(1);
		}).doesNotThrowAnyException();
		verify(userService, times(1)).getUser(1);
	}
	
	@Test
	public void deleteUser_nullUserFail() {
		when(userService.getUser(1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.deleteUser(1);
		});
	}
	
	@Test
	public void saveNewPassword_success() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(PasswordGenerationUtil.getSaltString())
			.thenReturn("123");
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThatCode(() -> { 
			userService.saveNewPassword("mail");
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveNewPassword_nullEmailFail() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail(null))
			.thenReturn(null);
		when(PasswordGenerationUtil.getSaltString())
			.thenReturn("123");
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.saveNewPassword("mail");
		});
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
