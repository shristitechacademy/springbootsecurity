package com.bookapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookapp.service.JwtUserServiceImpl;
import com.bookapp.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserServiceImpl userServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// to check for the header with name authorization
		String header = request.getHeader("Authorization");
		System.out.println(header);
		String username = null;
		String token = null;
		// Bearer ey2wer234fsdwr223
		if (header != null && header.startsWith("Bearer ")) {
			// retrieve the token from the header
			// Bearer ey2wer234fsdwr223
			token = header.substring(7);
			// extract username from the token
			username = jwtTokenUtil.getUsernameFromToken(token);
			System.out.println(username);
		} else {
			logger.warn("invalid token");
		}
		// chekc the security context whether the authentication is available or null
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
			// check if the username exists in the database
			UserDetails userDetails =  userServiceImpl.loadUserByUsername(username);
			// after getting the userdetails validate the token
			// if the token is valid set the security contect to the authenticationtype
			if(jwtTokenUtil.validateToken(token, userDetails)){
				// provide of the type of authentication that we are using
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				// set the security contect to the authenticationtype
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		
		}
		// this will be called first for users/register and users/authenticate
		// if there is no header proceed to the next filter
		filterChain.doFilter(request, response);

	}

}
