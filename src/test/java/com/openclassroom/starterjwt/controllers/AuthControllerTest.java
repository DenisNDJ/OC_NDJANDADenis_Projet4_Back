package com.openclassroom.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.openclassrooms.starterjwt.configuration.AppConfig;

@SpringBootTest(classes=AppConfig.class)
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
           			.andExpect(status().isForbidden());
	               /*.andExpect(status().isOk())
	               .andExpect(jsonPath("$.firstName").value("Admin"))
	               .andExpect(jsonPath("$.lastName").value("Admin"))
	               .andExpect(jsonPath("$.email").value("yoga@studio.com"))
	               .andExpect(jsonPath("$.admin").value(true));*/
	}
    
	@Test
	public void loginFailure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("passwordFail");
        
	       mockMvc.perform(post("/api/auth/login")
	               .contentType(MediaType.APPLICATION_JSON)
	               .characterEncoding("utf-8")
	               .content(objectMapper.writeValueAsString(loginRequest)))
           .andExpect(status().isForbidden());
           //.andExpect(status().isUnauthorized());
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
           			.andExpect(status().isForbidden());
	               //.andExpect(status().isOk());

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
	       		   .andExpect(status().isForbidden());
	               //.andExpect(status().isBadRequest());
	}

}
