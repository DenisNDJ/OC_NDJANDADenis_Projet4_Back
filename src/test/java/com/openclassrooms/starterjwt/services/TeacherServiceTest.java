package com.openclassroom.starterjwt.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
	
	Teacher teacher1 = new Teacher(
			(long)1,
			"Ndjanda",
			"Denis",
			null,
			null) ;
	
	Teacher teacher2 = new Teacher(
			(long)1,
			"Ndjanda",
			"Denis",
			null,
			null) ;
	
	List<Teacher> lstTeacher = Arrays.asList(teacher1,teacher2);
	
	@Mock
	TeacherRepository teacherRepository;
	@InjectMocks
	TeacherService teacherService;
	
	@Test
	@DisplayName("Get Teachers")
	void findAll() {
		List<Teacher> lstTeacherDb = new ArrayList<Teacher>();
				
		when(teacherRepository.findAll()).thenReturn(lstTeacher);
		
		lstTeacherDb = teacherService.findAll();
		
		verify(teacherRepository, times(1)).findAll();
		assertThat(lstTeacherDb.size()).isEqualTo(2);
		assertThat(lstTeacherDb).contains(teacher1,teacher2);
	}
	
	@Test
	@DisplayName("Get Teacher by ID")
	void findById() {
		Teacher teacherDb;
				
		when(teacherRepository.findById((long)1)).thenReturn(Optional.of(teacher1));
		
		teacherDb = teacherService.findById((long)1);
		
		verify(teacherRepository, times(1)).findById((long)1);
		assertThat(teacherDb).isEqualTo(teacher1);
	}

}

