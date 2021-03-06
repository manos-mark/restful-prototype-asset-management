package com.manos.prototype.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.manos.prototype.dto.UserRequestDto;
import com.manos.prototype.entity.User;
import com.manos.prototype.exception.ApplicationException;
import com.manos.prototype.exception.EntityNotFoundException;
import com.manos.prototype.finder.UserFinder;
import com.manos.prototype.security.UserDetailsImpl;
import com.manos.prototype.util.PasswordGenerationUtil;
import com.manos.prototype.util.SecurityUtil;
import com.pastelstudios.db.GenericFinder;
import com.pastelstudios.db.GenericGateway;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {GenericGateway.class, GenericFinder.class, SecurityUtil.class, PasswordGenerationUtil.class})
public class UserServiceTest {
	
	@Mock
	private UserFinder userDao;

	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock 
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private GenericFinder finder;
	
	@Mock
	private GenericGateway gateway;
	
	@Test
	public void getCurrentUserDetails_nullUserFail() {
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(null);
		
		assertThatCode(() -> { 
			userService.getCurrentUserDetails();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getCurrentUserDetails_notAcceptedCookies_success() {
		User mockUser = createMockUser();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, mockUserDetails.getId()))
			.thenReturn(mockUser);
		
		assertThat(mockUserDetails).isNotNull();
		assertThat(mockUser).isNotNull();
		assertThat(mockUser.getAcceptedCookiesDatetime()).isNull();
		assertThatCode(() -> { 
			userService.getCurrentUserDetails();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getCurrentUserDetails_acceptedCookies_success() {
		User mockUser = createMockUser();
		mockUser.setAcceptedCookiesDatetime(LocalDateTime.now());
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, mockUserDetails.getId()))
			.thenReturn(mockUser);
		
		assertThat(mockUserDetails).isNotNull();
		assertThat(mockUser).isNotNull();
		assertThat(mockUser.getAcceptedCookiesDatetime()).isNotNull();
		assertThatCode(() -> { 
			userService.getCurrentUserDetails();
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void getUser_success() {
		User mockUser = createMockUser();
		when(finder.findById(User.class, 1))
			.thenReturn(mockUser);
		
		assertThat(mockUser).isNotNull();
		assertThatCode(() -> { 
			userService.getUser(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateUserPassword_nullUser_fail() {
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, 1L))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.updateUserPassword(null, null);;
		});
	}
	
	@Test
	public void updateUserPassword_nullUserDetails_fail() {
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.updateUserPassword(null, null);;
		});
	}
	
	@Test
	public void updateUserPassword_passwordsDontMatch_fail() {
		User mockUser = createMockUser();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, 1L))
			.thenReturn(mockUser);
		when(passwordEncoder.matches("000", "789"))
			.thenReturn(false);
		
		assertThatExceptionOfType(ApplicationException.class)
			.isThrownBy(() -> {
				userService.updateUserPassword("000", "789");
			});
	}
	
	@Test
	public void updateUserPassword_cannotSaveNewPassword_fail() {
		User mockUser = createMockUser();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(finder.findById(User.class, 1L))
			.thenReturn(mockUser);
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(passwordEncoder.matches("000", "123"))
			.thenReturn(true);
		when(passwordEncoder.encode("123"))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.updateUserPassword("000", "789");
			});
	}
	
	@Test
	public void updateUserPassword_success() {
		User mockUser = createMockUser();
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(finder.findById(User.class, 1L))
			.thenReturn(mockUser);
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(passwordEncoder.matches("123", "123"))
			.thenReturn(true);
		when(passwordEncoder.encode("789"))
			.thenReturn("$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K");
			
		assertThatCode(() -> { 
			userService.updateUserPassword("123", "789");
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
	
	@Test
	public void deleteUser_success() {
		User mockUser = createMockUser();
		
		when(userService.getUser(1))
			.thenReturn(mockUser);

		assertThatCode(() -> { 
			userService.deleteUser(1);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void saveNewPassword_nullUser_fail() {
		when(userDao.getUserByEmail("mail"))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
		.isThrownBy(() -> {
			userService.saveNewPassword("mail");
		});
	}
	
	@Test
	public void saveNewPassword_success() {
		PowerMockito.mockStatic(PasswordGenerationUtil.class);
		User mockUser = createMockUser();
		
		when(userDao.getUserByEmail("mail"))
			.thenReturn(mockUser);
		when(PasswordGenerationUtil.getSaltString())
			.thenReturn("789");
		
		assertThatCode(() -> { 
			userService.saveNewPassword("mail");
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateUser_nullUserDetails_fail() {
		when(finder.findById(User.class, 1))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.updateUser(null);
			});
	}
	
	@Test
	public void updateUser_nullUser_fail() {
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(SecurityUtil.class);
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, 1L))
			.thenReturn(null);
		
		assertThatExceptionOfType(EntityNotFoundException.class)
			.isThrownBy(() -> {
				userService.updateUser(null);
			});
	}
	
	@Test
	public void updateUser_success() {
		PowerMockito.mockStatic(SecurityUtil.class);
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(PasswordGenerationUtil.class);
		User mockUser = createMockUser();
		UserRequestDto mockDto = new UserRequestDto();
		mockDto.setAcceptedCookies(false);
		mockDto.setEmail("mmail");
		mockDto.setFirstName("Name");
		mockDto.setLastName("last");
		
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		when(finder.findById(User.class, 1L))
			.thenReturn(mockUser);
			
		assertThatCode(() -> { 
			userService.updateUser(mockDto);
		}).doesNotThrowAnyException();
	}
	
	@Test
	public void updateUser_acceptCookies_success() {
		PowerMockito.mockStatic(SecurityUtil.class);
		UserDetailsImpl mockUserDetails = createMockUserDetails();
		PowerMockito.mockStatic(PasswordGenerationUtil.class);
		User mockUser = createMockUser();
		UserRequestDto mockDto = new UserRequestDto();
		mockDto.setAcceptedCookies(false);
		mockDto.setEmail("mmail");
		mockDto.setFirstName("Name");
		mockDto.setLastName("last");
		mockDto.setAcceptedCookies(true);
		
		when(finder.findById(User.class, 1L))
			.thenReturn(mockUser);
		when(SecurityUtil.getCurrentUserDetails())
			.thenReturn(mockUserDetails);
		
		assertThatCode(() -> { 
			userService.updateUser(mockDto);
		}).doesNotThrowAnyException();
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
