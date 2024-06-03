package com.bookapp.model;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "appuser")
public class AppUser {
	@Id
	@GeneratedValue
	private Integer userId;
	@Column(unique = true)
	private String username;
	private String password;
	
	@ElementCollection (fetch=FetchType.EAGER)
	@CollectionTable(name="user_authorities",
					joinColumns = @JoinColumn(name="user_id") )
	private Set<String> authorities;
	
	public AppUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppUser(String username, String password, Set<String> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
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

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "AppUser [userId=" + userId + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + "]";
	}
	
	
	
	
}
