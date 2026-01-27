package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class SessionControllerTest {
	@Autowired
    private MockMvc mockMvc;
	@Autowired
    private SessionMapper sessionMapper;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	String mockAuthorization;
	
	private User mockUser = new User(1L,
								"yoga@studio.com",
								"Admin",
								"Admin",
								"$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq",
								false,
								null,
								null);
	
	@BeforeEach
    public void setupAuth() throws JsonProcessingException, Exception {
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
        
        mockAuthorization = mockJwtResponse.getType()+" "+mockJwtResponse.getToken();
    }
    
	@Test
	public void getSessionbyId() throws Exception {
        mockMvc.perform(get("/api/session/999")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isNotFound());
        
        mockMvc.perform(get("/api/session/11")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk())
    	        .andExpect(jsonPath("$.id").value(11))
    	        .andExpect(jsonPath("$.name").value("Yoga du soir"))
    	        .andExpect(jsonPath("$.description").value("le yoga du soir"))
    	        .andExpect(jsonPath("$.teacher_id").value(1));
	}
    
	@Test
	public void getSession() throws Exception {
		objectMapper.registerModule(new JavaTimeModule());
        
		MvcResult mockResponse = mockMvc.perform(get("/api/session")
				            	.contentType(MediaType.APPLICATION_JSON)
				    	        .characterEncoding("utf-8")
				    	        .header("Authorization", mockAuthorization))
				            	.andExpect(status().isOk())
				            	.andReturn();
        
        String json = mockResponse.getResponse().getContentAsString();
        List<SessionDto> sessions = objectMapper.readValue(json, new TypeReference<>(){});

		assertThat(sessions).isNotNull();
		assertThat(sessions.size()).isEqualTo(3);
		assertThat(sessions.get(0).getName()).isEqualTo("Yoga du soir");
		assertThat(sessions.get(1).getName()).isEqualTo("Yoga du matin");
		assertThat(sessions.get(2).getName()).isEqualTo("Veneration du sol");        
	}
	    
	@Test
	public void updateSession() throws Exception {
		SessionDto mockSession = new SessionDto(null, "Yoga des poules",new Date(),(long)2,"le Yoga des poules",null,null,null);
		mockSession.setId(11L);
		objectMapper.registerModule(new JavaTimeModule());
		
		MvcResult mockResponse = mockMvc.perform(put("/api/session/11")
								.contentType(MediaType.APPLICATION_JSON)
								.characterEncoding("utf-8")
								.header("Authorization", mockAuthorization)
								.content(objectMapper.writeValueAsString(mockSession)))
								.andExpect(status().isOk())
								.andReturn();
		
        String json = mockResponse.getResponse().getContentAsString();
        SessionDto mockSessionResponse = objectMapper.readValue(json, SessionDto.class);
        
		assertThat(mockSessionResponse.getName()).isEqualTo(mockSession.getName());
		assertThat(mockSessionResponse.getDescription()).isEqualTo(mockSession.getDescription());
		assertThat(mockSessionResponse.getId()).isEqualTo(mockSession.getId());  
		assertThat(mockSessionResponse.getDate()).isEqualTo(mockSession.getDate());  		
	}
	
	@Test
	public void deleteSessionById() throws Exception {        
        mockMvc.perform(delete("/api/session/999")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isNotFound()); 
        
        mockMvc.perform(delete("/api/session/12")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk());
	}
	
	@Test
	public void createSession() throws Exception {
		Teacher mockTeacher = new Teacher(4L, "ATREIDIS", "Paul" , null, null);
		objectMapper.registerModule(new JavaTimeModule());
		Session mockSession = new Session(
									"Yoga du volcan",
									new Date(),
									"le yoga du volcan",mockTeacher ,
									null,
									LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(),
									LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay());
		
		MvcResult mockResponse = mockMvc.perform(post("/api/session")
				            	.contentType(MediaType.APPLICATION_JSON)
				    	        .characterEncoding("utf-8")
				    	        .content(objectMapper.writeValueAsString(sessionMapper.toDto(mockSession)))
				    	        .header("Authorization", mockAuthorization))
				            	.andExpect(status().isOk())
				            	.andReturn();
	}
	
	@Test
	public void participate() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/1")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk());

        mockMvc.perform(delete("/api/session/1/participate/1")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk());
	}
    
}









