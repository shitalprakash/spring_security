package com.bootstrapping.demo.auth;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUser implements UserDetails {
	
	private Set<? extends GrantedAuthority> lstGrantedAuthority;
	private boolean isCredentialsNonExpired;
	private boolean isEnabled;
	private boolean isAccountNonExpired; 
	private boolean isAccountNonLocked;
	private String password;
	private String username;
	
	

	public ApplicationUser(Set<? extends GrantedAuthority> lstGrantedAuthority, boolean isCredentialsNonExpired,
			boolean isEnabled, boolean isAccountNonLocked,boolean isAccountNonExpired, String password, String username) {
		this.lstGrantedAuthority = lstGrantedAuthority;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isEnabled = isEnabled;
		this.isAccountNonLocked = isAccountNonLocked;
		this.password = password;
		this.username = username;
		this.isAccountNonExpired=isAccountNonExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return lstGrantedAuthority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

}
