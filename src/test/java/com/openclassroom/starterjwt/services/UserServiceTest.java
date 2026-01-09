package com.openclassroom.starterjwt.testing.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	User user = new User(
			(long)1,
			"denis@gmail.com",
			"Ndjanda",
			"Denis",
			"d@nis!987",
			true,
			null,
			null);
	
	@InjectMocks
	UserService userService;
	@Mock
	UserRepository userRepository;
	
	@Test
	@DisplayName("Save User")
	void save() {
		when(userRepository.save(user)).thenReturn(user);
		
		userService.save(user);

		verify(userRepository, times(1)).save(user);
	}	
	
	@Test
	@DisplayName("Delete User")
	void delete() {
		doNothing().when(userRepository).deleteById((long)1);
		
		userService.delete((long)1);

		verify(userRepository, times(1)).deleteById((long)1);
	}	
	
	@Test
	@DisplayName("Find User by ID")
	void findById() {
		User dbUser;
		
		when(userRepository.findById((long)1)).thenReturn(Optional.of(user));
		
		dbUser = userService.findById((long)1);

		verify(userRepository, times(1)).findById((long)1);
		assertThat(dbUser).isEqualTo(user);
	}	
	
	@Test
	@DisplayName("Find User by email")
	void findByEmail() {
		User dbUser;
		
		when(userRepository.findByEmail("denis@gmail.com")).thenReturn(Optional.of(user));
		
		dbUser = userService.findByEmail("denis@gmail.com");

		verify(userRepository, times(1)).findByEmail("denis@gmail.com");
		assertThat(dbUser).isEqualTo(user);
	}	
	
	@Test
	@DisplayName("Find if User exist via email")
	void existsByEmail() {
		boolean userExistOrNot;
		
		when(userRepository.findByEmail("denis@gmail.com")).thenReturn(Optional.of(user));
		
		userExistOrNot = userService.existsByEmail("denis@gmail.com");

		verify(userRepository, times(1)).findByEmail("denis@gmail.com");
		assertThat(userExistOrNot).isEqualTo(true);
	}

}
