package com.openclassroom.starterjwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.openclassrooms.starterjwt.configuration.AppConfig;

@SpringBootTest(classes = {AppConfig.class})
public class SpringBootSecurityJwtApplicationTest {
	
	@Test
	public void contextLoad() {
		
	}

}