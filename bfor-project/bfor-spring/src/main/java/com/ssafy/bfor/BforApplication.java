package com.ssafy.bfor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BforApplication {

	public static void main(String[] args) {
		SpringApplication.run(BforApplication.class, args);
	}

}
