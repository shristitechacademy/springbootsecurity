package com.bookapp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookapp.model.AppUser;
import com.bookapp.service.AppUserServiceImpl;

@RestController
public class AppUserController {
	
	private AppUserServiceImpl appUserServiceImpl;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setAppUserServiceImpl(AppUserServiceImpl appUserServiceImpl) {
		this.appUserServiceImpl = appUserServiceImpl;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


	@PostMapping("/appUsers")
	public AppUser saveUser(@RequestBody AppUser appUser) {
		String password = appUser.getPassword();
		String newpassword = passwordEncoder.encode(password);
		String username = appUser.getUsername();
		Set<String> authorities = appUser.getAuthorities();
		
		AppUser nappUser = new AppUser(username, newpassword, authorities);
		// call the addUser method of service class
		return appUserServiceImpl.addUser(nappUser);
		
	}
}
