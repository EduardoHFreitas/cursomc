package com.eduardo.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	public boolean isTokenValido(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null) {
			String email = claims.getSubject();
			Date expiration = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			
			if (email != null && expiration != null && now.before(expiration)) {
				return true;
			}
		}
		
		return false;
	}

	public String getEmail(String token) {
		Claims claims = getClaims(token);
		
		if (claims != null && claims.getSubject() != null) {
			return claims.getSubject();
		}
		
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
}
