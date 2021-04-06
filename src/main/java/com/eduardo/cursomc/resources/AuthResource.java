package com.eduardo.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.cursomc.dto.EmailDTO;
import com.eduardo.cursomc.security.JWTUtil;
import com.eduardo.cursomc.security.User;
import com.eduardo.cursomc.services.AuthService;
import com.eduardo.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		User user = UserService.getAuthenticatedUser();
		String token = jwtUtil.generateToken(user.getUsername());
		
		response.addHeader("Authorization", "Bearer " + token);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO email) {
		authService.sendNewPassword(email.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}
