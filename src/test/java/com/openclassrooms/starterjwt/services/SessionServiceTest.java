package com.openclassrooms.starterjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

	@InjectMocks
	SessionService sessionService;
	@Mock
	SessionRepository sessionRepository;
	@Mock
	UserRepository userRepository;
	
	User user1 = new User(
			(long)1,
			"denis@gmail.com",
			"Ndjanda",
			"Denis",
			"d@nis!987",
			true,
			null,
			null);
	
	User user2 = new User(
			(long)2,
			"miles@gmail.com",
			"Ndjanda",
			"Miles",
			"miles!987",
			true,
			null,
			null);	
	
	User user3 = new User(
			(long)3,
			"paul@gmail.com",
			"Ndjanda",
			"paul",
			"paul!987",
			true,
			null,
			null);
	
	List<User> participants = new ArrayList<>();
	
	Teacher teacher1 = new Teacher(
			(long)1,
			"Ndjanda",
			"Denis",
			null,
			null) ;

	Session session1 = new Session(
			(long)1,
			"1er session",
			new Date(),
			"1er session de 2026",
			teacher1,
			participants,
			LocalDateTime.now(),
			LocalDateTime.now());
	
	Session session2 = new Session(
			(long)2,
			"1er session",
			new Date(),
			"1er session de 2026",
			teacher1,
			participants,
			LocalDateTime.now(),
			LocalDateTime.now());
	
	
	List<Session> sessions = new ArrayList<Session>();
	
	@BeforeEach
	public void init() {
		participants.add(user1);
		participants.add(user2);
		sessions.add(session1);
		sessions.add(session2);
	}
	
	@AfterEach
	public void clearList() {
		participants.clear();
		sessions.clear();
	}
	
	@Test
	@DisplayName("Create Session")
	void create() {
		Session dbSession;
		
		when(sessionRepository.save(session1)).thenReturn(session1);
		
		dbSession = sessionService.create(session1);

		assertThat(dbSession).isEqualTo(session1);
		verify(sessionRepository, times(1)).save(session1);
	}	
	
	@Test
	@DisplayName("Delete Session")
	void delete() {
		
		doNothing().when(sessionRepository).deleteById((long)1);
		
		sessionService.delete((long)1);
		verify(sessionRepository, times(1)).deleteById((long)1);
	}	
	
	@Test
	@DisplayName("Find all Sessions")
	void findAll() {
		List<Session> dbSession;
		
		when(sessionRepository.findAll()).thenReturn(sessions);
		
		dbSession = sessionService.findAll();

		verify(sessionRepository, times(1)).findAll();
		assertThat(dbSession).isEqualTo(sessions);
	}	
	
	@Test
	@DisplayName("Get session by ID")
	void getById() {
		
		Session dbSession;
		
		when(sessionRepository.findById((long)1)).thenReturn(Optional.of(session1));
		
		dbSession = sessionService.getById((long)1);

		verify(sessionRepository, times(1)).findById((long)1);
		assertThat(dbSession).isEqualTo(session1);
	}		
	
	@Test
	@DisplayName("Update session")
	void update() {
		
		Session dbSession;
		
		when(sessionRepository.save(session1)).thenReturn(session2);
		
		dbSession = sessionService.update((long)2, session1);

		verify(sessionRepository, times(1)).save(session1);
		assertThat(dbSession).isEqualTo(session2);
	}		
	
	@Test
	@DisplayName("Add user to session participant")
	void participate() {
		when(sessionRepository.findById((long)1)).thenReturn(Optional.of(session1));
		when(userRepository.findById((long)3)).thenReturn(Optional.of(user3));
		
		sessionService.participate((long)1,(long)3);

		verify(sessionRepository, times(1)).findById((long)1);
		verify(userRepository, times(1)).findById((long)3);
	}		
	
	@Test
	@DisplayName("Add user to session participant but session empty")
	void participateEmptySession() {
		when(sessionRepository.findById((long)1)).thenReturn(Optional.empty());
		when(userRepository.findById((long)3)).thenReturn(Optional.of(user3));
		
		assertThrows(NotFoundException.class, ()->{
			sessionService.participate((long)1,(long)3);
		});
		
		verify(sessionRepository, times(1)).findById((long)1);
		verify(userRepository, times(1)).findById((long)3);
	}		
	
	@Test
	@DisplayName("Remove user from session participant")
	void noLongerParticipate() {
		when(sessionRepository.findById((long)1)).thenReturn(Optional.of(session1));
		when(sessionRepository.save(session1)).thenReturn(session1);
		
		sessionService.noLongerParticipate((long)1,(long)1);
		
		verify(sessionRepository, times(1)).findById((long)1);
		verify(sessionRepository, times(1)).save(session1);
	}	

}
