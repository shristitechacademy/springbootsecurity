package com.bookapp.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookapp.model.AppUser;
import com.bookapp.repository.AppUserRepository;

@Service
public class AppUserServiceImpl implements UserDetailsService {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByUsername(username)
			.orElseThrow(()-> new UsernameNotFoundException("username doesnot exist"));
		// get the values from AppUser and create a User object
		String uname  = appUser.getUsername();
		String password = appUser.getPassword();
		Set<String>authorities = appUser.getAuthorities();
		
		//Set of GrantedAuthority
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//		// itearte through authorities object
//		authorities.forEach((role)->{
//			GrantedAuthority auth =  new SimpleGrantedAuthority(role);
//			// add this object to  grantedAuthorities
//			grantedAuthorities.add(auth);
//		});
//		

		// java 8 map
//		authorities.stream().map(str->str.length()).collect(Collectors.toList());
		grantedAuthorities =  authorities.stream()
							.map(role->new SimpleGrantedAuthority(role))
			.collect(Collectors.toSet());
			
		
		// pass the properties
		User user = new User(uname,password,grantedAuthorities);
		return user;
	}

	public AppUser addUser(AppUser user) {
		return appUserRepository.save(user);
	}
}





