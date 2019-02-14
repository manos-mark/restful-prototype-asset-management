package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.manos.prototype.dao.UserDaoImpl;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.SecurityUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserDaoImpl userDao;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private SecurityUtil securityUtil;
	
	@Test
	public void saveUser_success() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(null);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			userService.saveUser(mockUser);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveUser_nullUserFail() {
		assertThatExceptionOfType(NullPointerException.class)
			.isThrownBy(() -> {
				userService.saveUser(null);
			});
	}
	
	@Test
	public void saveUser_nullPasswordFail() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.saveUser(mockUser);
			});
	}
	
	@Test
	public void updateUser_success() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			userService.updateUser(mockUser);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateUser_mailNotFoundFail() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail123"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatCode(() -> { 
			userService.updateUser(mockUser);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateUser_nullUserFail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.updateUser(null);
			});
	}
	
	@Test
	public void updateUser_nullPasswordFail() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn(null);
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.updateUser(mockUser);
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
