package com.bookapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bookapp.service.AppUserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebConfig {

//	bean for authentication,authorization and 
//	authentication provider
	
	// authentication
	@Bean
	public UserDetailsService userDetailsService() {
		 return new AppUserServiceImpl();
		
	}
	// passwordEncoder
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	// bean for AuthenticationProvider
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	// authorization bean
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http.csrf().disable()
		.authorizeHttpRequests(auth->{
			auth.requestMatchers("/appUsers").permitAll();
			auth.requestMatchers("/book-api/admin/**").hasAuthority("ADMIN");
			auth.requestMatchers("/book-api/user/**").hasAnyAuthority("ADMIN","USER");
		})
		.httpBasic()
		.and()
		.build();
		
	}
}
