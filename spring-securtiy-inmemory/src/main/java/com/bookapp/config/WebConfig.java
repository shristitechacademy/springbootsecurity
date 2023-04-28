package com.bookapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebConfig {
	// inmemory authentication
	// authentication
	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		//create users by adding credentials
		UserDetails admin = User.withUsername("admin")
				.password(passwordEncoder.encode("admin123"))
				.roles("ADMIN","USER")
				.build();
		
		UserDetails user = User.withUsername("priya")
				.password(passwordEncoder.encode("priya123"))
				.roles("USER")
				.build();
				
			return new InMemoryUserDetailsManager(admin,user);	
		
	}
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
//	authorization
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		return http.csrf().disable()
		.authorizeHttpRequests((auth)->{
			auth.antMatchers("/").permitAll();
			auth.antMatchers("/user/**").hasAnyRole("USER","ADMIN");
			auth.antMatchers("/admin/**").hasRole("ADMIN");
		})
		.formLogin()
		.and()
		.build();
		
			
		
		
		
	}
	
}

















