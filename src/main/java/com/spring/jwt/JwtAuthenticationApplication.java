package com.spring.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "com.spring.jwt") 
@PropertySource("classpath:application.properties")  // Make sure this file is correctly loaded
@EnableConfigurationProperties
public class JwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}
	
//	http://localhost:8080/token  postrequest key= Authorization, value =Bearer 3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b

	// http://localhost:8080/welcome  getrequest key-Authorization , value=Berer generatedtoken

}
