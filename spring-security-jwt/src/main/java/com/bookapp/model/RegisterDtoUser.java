package com.bookapp.model;

import java.util.Set;

public class RegisterDtoUser {
	private Integer userId;
	private String username;
	private String password;
	
	private Set<String> roles; // admin, user

	public RegisterDtoUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegisterDtoUser(String username, String password, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "JwtUser [userId=" + userId + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ "]";
	}

}
