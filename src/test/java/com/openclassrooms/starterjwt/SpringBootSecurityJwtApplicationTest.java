package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.openclassrooms.starterjwt.configuration.AppConfig;

@SpringBootTest
@ActiveProfiles("test")
public class SpringBootSecurityJwtApplicationTest {
	
	@Test
	public void contextLoad() {
		
	}

}