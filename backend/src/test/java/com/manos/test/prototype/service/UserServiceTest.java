package com.manos.test.prototype.service;

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

import com.manos.prototype.dao.UserDao;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.service.UserServiceImpl;
import com.manos.prototype.util.SecurityUtil;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@Mock
	private UserDao userDao;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private SecurityUtil securityUtil;
	
//	@Test
//	public void saveNewPassword_success() {
//		User mockUser = createMockUser();
//
//		when(userDao.getUserByEmail("mail"))
//			.thenReturn(mockUser);
//		when(PasswordGenerationUtil.getSaltString())
//			.thenReturn("123");
//		when(passwordEncoder.encode("123"))
//			.thenReturn("aBc1");
//		
//		assertThatCode(() -> { 
//			userService.saveNewPassword("mail");
//		}).doesNotThrowAnyException();
//	}
//	
//	@Test
//	public void saveNewPassword_nullEmailFail() {
//		when(userDao.getUserByEmail(null))
//			.thenReturn(null);
//		when(PasswordGenerationUtil.getSaltString())
//			.thenReturn("123");
//		when(passwordEncoder.encode("123"))
//			.thenReturn("aBc1");
//		
//		assertThatExceptionOfType(EntityNotFoundException.class)
//			.isThrownBy(() -> {
//				userService.saveNewPassword("mail");
//			});
//	}
//	
//	@Test
//	public void getCurrentUserDetails() {
//		UserDetailsImpl mockUserDetails = createMockUserDetails();
//		
//		when(SecurityUtil.getCurrentUserDetails())
//			.thenReturn(mockUserDetails);
//		
//		UserDetailsImpl userDetails = userService.getCurrentUserDetails();
//		
//		assertThat(userDetails).isEqualTo(mockUserDetails);
//		assertThat(userDetails)
//			.isEqualToComparingFieldByFieldRecursively(mockUserDetails);
//	}
//	
//	@Test
//	public void getCurrentUserDetails_nullUserFail() {
//		when(SecurityUtil.getCurrentUserDetails())
//			.thenReturn(null);
//		
//		assertThatExceptionOfType(EntityNotFoundException.class)
//			.isThrownBy(() -> {
//				userService.getCurrentUserDetails();
//			});
//	}
//	
//	@Test 
//	public void findByEmail_success() {
//		User mockUser = createMockUser();
//		
//		when(userDao.getUserByEmail("mail"))
//			.thenReturn(mockUser);
//		
//		User user = userService.findByEmail("mail");
//		
//		assertThat(user).isEqualTo(mockUser);
//		assertThat(user).isEqualToComparingFieldByFieldRecursively(mockUser);
//	}
//	
//	@Test 
//	public void findByEmail_nullEmailFail() {
//		assertThatExceptionOfType(EntityNotFoundException.class)
//			.isThrownBy(() -> {
//				userService.findByEmail(null);
//			});
//	}
	
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
	public void saveUser_userExistsFail() {
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(passwordEncoder.encode("123"))
			.thenReturn("aBc1");
		
		assertThat(mockUser).isNotNull();
		assertThat(mockUser).hasNoNullFieldsOrProperties();
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.saveUser(mockUser);
			});
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
