package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class TeacherControllerTest {
	@Autowired
    private MockMvc mockMvc;
	
	private ObjectMapper objectMapper = new ObjectMapper();
    
    private String setupTokenAuth() throws JsonProcessingException, Exception {
		LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        
        MvcResult mockResponse = mockMvc.perform(post("/api/auth/login")
        	.contentType(MediaType.APPLICATION_JSON)
	        .characterEncoding("utf-8")
	        .content(objectMapper.writeValueAsString(loginRequest)))
        	.andExpect(status().isOk())
	        .andExpect(jsonPath("$.firstName").value("Admin"))
	        .andReturn();
        
        String json = mockResponse.getResponse().getContentAsString();
        JwtResponse mockJwtResponse = objectMapper.readValue(json, JwtResponse.class);
        
        return mockJwtResponse.getType()+" "+mockJwtResponse.getToken();
    }
    
	@Test
	public void getTeacherbyId() throws Exception {
		String mockAuthorization = setupTokenAuth();
        
        mockMvc.perform(get("/api/teacher/1")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk())
    	        .andExpect(jsonPath("$.id").value(1))
    	        .andExpect(jsonPath("$.firstName").value("Paul"))
    	        .andExpect(jsonPath("$.lastName").value("ATREIDIS"));
	}
    
	@Test
	public void getTeachers() throws Exception {
		String mockAuthorization = setupTokenAuth();
		
		objectMapper.registerModule(new JavaTimeModule());
        
		MvcResult mockResponse = mockMvc.perform(get("/api/teacher")
				            	.contentType(MediaType.APPLICATION_JSON)
				    	        .characterEncoding("utf-8")
				    	        .header("Authorization", mockAuthorization))
				            	.andExpect(status().isOk())
				            	.andReturn();
        
        String json = mockResponse.getResponse().getContentAsString();
        List<TeacherDto> teacher = objectMapper.readValue(json, new TypeReference<>(){});

		assertThat(teacher).isNotNull();
		assertThat(teacher.size()).isEqualTo(4);
		assertThat(teacher.get(0).getFirstName()).isEqualTo("Paul");
		assertThat(teacher.get(1).getFirstName()).isEqualTo("Marie");
		assertThat(teacher.get(2).getFirstName()).isEqualTo("Hans");  
		assertThat(teacher.get(3).getFirstName()).isEqualTo("Sam");        
	}
    
}









