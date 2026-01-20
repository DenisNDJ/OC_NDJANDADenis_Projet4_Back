package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class AuthControllerTest {
	@Autowired
    private MockMvc mockMvc;	
    
    private ObjectMapper objectMapper = new ObjectMapper();    
    
	@Test
	public void login() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        
        mockMvc.perform(post("/api/auth/login")
        	.contentType(MediaType.APPLICATION_JSON)
	        .characterEncoding("utf-8")
	        .content(objectMapper.writeValueAsString(loginRequest)))
        	.andExpect(status().isOk())
	        .andExpect(jsonPath("$.firstName").value("Admin"))
	        .andExpect(jsonPath("$.lastName").value("Admin"))
	        .andExpect(jsonPath("$.username").value("yoga@studio.com"))
	        .andExpect(jsonPath("$.admin").value(true));
	}
    
	@Test
	public void loginFailure() throws Exception {
        LoginRequest loginRequestFail = new LoginRequest();
        loginRequestFail.setEmail("yoga@studio.com");
        loginRequestFail.setPassword("passwordFail");
        
	       mockMvc.perform(post("/api/auth/login")
	               .contentType(MediaType.APPLICATION_JSON)
	               .characterEncoding("utf-8")
	               .content(objectMapper.writeValueAsString(loginRequestFail)))
           		   .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void register() throws Exception {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setFirstName("Marc");
		signupRequest.setLastName("Anthoine");
		signupRequest.setEmail("marc@gmail.com");
		signupRequest.setPassword("test!1234");
        
	       mockMvc.perform(post("/api/auth/register")
	               .contentType(MediaType.APPLICATION_JSON)
	               .characterEncoding("utf-8")
	               .content(objectMapper.writeValueAsString(signupRequest)))
	               .andExpect(status().isOk());

	}
	
	@Test
	public void registerFail() throws Exception {
		SignupRequest signupRequest = new SignupRequest();
		signupRequest.setFirstName("Marc");
		signupRequest.setLastName("Anthoine");
		signupRequest.setEmail("yoga@studio.com");
		signupRequest.setPassword("test!1234");
        
	       mockMvc.perform(post("/api/auth/register")
	               .contentType(MediaType.APPLICATION_JSON)
	               .characterEncoding("utf-8")
	               .content(objectMapper.writeValueAsString(signupRequest)))
	               .andExpect(status().isBadRequest());
	}

}
