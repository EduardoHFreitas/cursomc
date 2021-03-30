package com.eduardo.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.eduardo.cursomc.services.DatabaseService;

@Configuration
@Profile("test")
public class ProfileTestConfiguration {

	@Autowired
	private DatabaseService databaseService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		databaseService.instantiateDatabase();
		
		return true;
	}
}
