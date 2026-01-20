package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
    private MockMvc mockMvc;	
    
    
    private String setupTokenAuth() throws JsonProcessingException, Exception {
		LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        ObjectMapper objectMapper = new ObjectMapper();
        
        MvcResult mockResponse = mockMvc.perform(post("/api/auth/login")
        	.contentType(MediaType.APPLICATION_JSON)
	        .characterEncoding("utf-8")
	        .content(objectMapper.writeValueAsString(loginRequest)))
        	.andExpect(status().isOk())
	        .andExpect(jsonPath("$.username").value("yoga@studio.com"))
	        .andReturn();
        
        String json = mockResponse.getResponse().getContentAsString();
        JwtResponse mockJwtResponse = objectMapper.readValue(json, JwtResponse.class);
        
        return mockJwtResponse.getType()+" "+mockJwtResponse.getToken();
    }
    
	@Test
	public void getUserbyId() throws Exception {
		String mockAuthorization = setupTokenAuth();
        
        mockMvc.perform(get("/api/user/1")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk())
    	        .andExpect(jsonPath("$.id").value(1))
    	        .andExpect(jsonPath("$.firstName").value("Admin"))
    	        .andExpect(jsonPath("$.lastName").value("Admin"))
    	        .andExpect(jsonPath("$.email").value("yoga@studio.com"));
	}
    
	@Test
	public void deleteUserById() throws Exception {
		String mockAuthorization = setupTokenAuth();
        
        mockMvc.perform(delete("/api/user/1")
            	.contentType(MediaType.APPLICATION_JSON)
    	        .characterEncoding("utf-8")
    	        .header("Authorization", mockAuthorization))
            	.andExpect(status().isOk());
	}
    
	

}
