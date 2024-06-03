package com.bookapp.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.bookapp.model.JwtUser;
import com.bookapp.repository.IJwtUserRepository;

@Service
public class JwtUserServiceImpl implements UserDetailsManager {
	
	private IJwtUserRepository userRepository;
	
	
	@Autowired
	public void setUserRepository(IJwtUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JwtUser jwtUser = userRepository.findByUsername(username)
		        .orElseThrow(()-> new UsernameNotFoundException("username not found"));
		// create the UserDetails object
		String uname = jwtUser.getUsername();
		String password= jwtUser.getPassword();
		// get the roles and convert but it is done in jwtuser already
//		Set<String> roles = jwtUser.getRoles();

//		Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
//		UserDetails userDetails  =  new User(uname,password,authorities);
		UserDetails userDetails  =  new User(uname,password,jwtUser.getAuthorities());
		return userDetails;
	}
	// add jwtuser to the database
	@Override
	public void createUser(UserDetails user) {
		// incoming is userdetails object downcast to convert tojwtuser
		JwtUser jwtUser = (JwtUser)user;
		userRepository.save(jwtUser);
	}

	@Override
	public void updateUser(UserDetails user) {	}

	@Override
	public void deleteUser(String username) {	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {	}

	@Override
	public boolean userExists(String username) {
		return false;
	}

}
