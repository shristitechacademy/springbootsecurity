package com.bookapp.controllers;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapp.model.JwtResponse;
import com.bookapp.model.JwtUser;
import com.bookapp.model.LoginDtoUser;
import com.bookapp.model.RegisterDtoUser;
import com.bookapp.service.JwtUserServiceImpl;
import com.bookapp.util.JwtTokenUtil;

@RestController
@RequestMapping("/user-api/v1")
public class JwtUserController {
	
	private PasswordEncoder passwordEncoder;
	private JwtUserServiceImpl userServiceImpl;
	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtResponse jwtResponse; // the class to carry the token
	@Autowired	
	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	@Autowired
	public void setUserServiceImpl(JwtUserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}
	
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	// this is the url that gets called first
	@PostMapping("/users/register")
	public ResponseEntity<Void> registerUser(@RequestBody RegisterDtoUser regUser ){
		// get the values of reguser from request body 
		String username = regUser.getUsername();
		String password = regUser.getPassword();
		Set<String> roles = regUser.getRoles();
		// encode the password
		String npass = passwordEncoder.encode(password);
		// create a jwtuser object and set the values
		JwtUser jwtUser = new JwtUser(username, npass, roles);
		// call the createUser of JwtUserServiceImpl
		userServiceImpl.createUser(jwtUser);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	// this url will be called to authenticate the user
	// if user is authenticated , then generate token and return it as response
	@PostMapping("/users/authenticate")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginDtoUser loginUser ){
		// get the username and password
		String username = loginUser.getUsername();
		String password = loginUser.getPassword();
		// authentiate the username and password using authenticationManager
		authenticate(username, password);
		UserDetails userDetails = userServiceImpl.loadUserByUsername(loginUser.getUsername());
		// generate the token
		String token = jwtTokenUtil.generateToken(userDetails);
		jwtResponse.setToken(token);
		return ResponseEntity.ok(jwtResponse);
	}
	
	public void authenticate(String username, String password) {
		// authentiate the username and password using authenticationManager
		try {
		 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			System.out.println(e.getMessage());
		}catch(LockedException e) {
			System.out.println(e.getMessage());
		}
	}
}






