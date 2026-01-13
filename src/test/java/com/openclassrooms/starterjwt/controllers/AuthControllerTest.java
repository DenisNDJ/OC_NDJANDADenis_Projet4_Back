package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
//@AutoConfigureMockMvc
public class AuthControllerTest {
	
	@Test
	public void contextLoad() {
		
	}
	//Class test under comment until issue fixed
	/*@Autowired
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
	        .andExpect(jsonPath("$.email").value("yoga@studio.com"))
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
	}*/

}
