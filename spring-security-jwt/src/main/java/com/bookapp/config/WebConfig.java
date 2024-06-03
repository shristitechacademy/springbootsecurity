package com.bookapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bookapp.service.JwtUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebConfig {
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	// beans for authentication 
	@Bean
	public UserDetailsManager userDetailsManager() {
		return new JwtUserServiceImpl();
	}
	// password encoder and DaoAuthentication Provider

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	// bean for Database specific AUthentication Provider
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsManager());
		return daoAuthenticationProvider;
	}
	
	
	// bean for authorization
	@Bean
	public SecurityFilterChain authorize(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
		     .exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
		     .authorizeHttpRequests(auth->auth
		    		 // authorize users with role as admin or user
		    		 .requestMatchers("/book-api/v1/books/**").hasAnyAuthority("ADMIN","USER")
		    		 //authorize users with role as admin
		    		 .requestMatchers("/book-api/v1/admin/**").hasAuthority("ADMIN")
		    		 .requestMatchers("/user-api/v1/users/register","/user-api/v1/users/authenticate").permitAll()
		    		 .anyRequest()
		    		 .authenticated());
		// attach the authentication provider
		http.authenticationProvider(authenticationProvider());
		// call the requestfilter before all
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
		
		
	}
	
}
