package com.eduardo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eduardo.cursomc.services.DatabaseService;
import com.eduardo.cursomc.services.IEmailService;
import com.eduardo.cursomc.services.mock.MockEmailService;

@Configuration
@Profile("test")
public class ProfileTestConfiguration {

	@Autowired
	private DatabaseService databaseService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if (strategy.equals("create")) {
			databaseService.instantiateDatabase();
		}
		
		return true;
	}
	
	@Bean
	public IEmailService emailService() {
		return new MockEmailService();
	}
}
