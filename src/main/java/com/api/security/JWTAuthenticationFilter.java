package com.api.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException{
		try {
			// Recuperer les informations d'authentification
			com.api.entities.User info = new ObjectMapper().readValue(req.getInputStream(), com.api.entities.User.class);

			// Retourner un authentification
			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					info.getEmail(),
					info.getPassword(),
					Collections.EMPTY_LIST
				)
			);
		} catch(IOException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
										    HttpServletResponse res,
								     	    FilterChain chain,
										    Authentication auth) throws IOException {
		
		// Si l'authentification reussie, construire le jeton JWT
		String jsonToken = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
										.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
										.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
										.compact();
		
		res.setStatus(HttpServletResponse.SC_OK);
		res.getWriter().write(JsonGenerator.buildJsonString(SecurityConstants.HEADER_STRING,
															SecurityConstants.TOKEN_PREFIX + " " + jsonToken));
	}
}
