package com.eduardo.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.eduardo.cursomc.security.User;

public class UserService {

	public static User getAuthenticatedUser() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
