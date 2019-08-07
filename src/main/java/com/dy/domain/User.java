package com.dy.domain;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements UserDetails {

//	private static final long serialVersionUID = 1L;
//
//	private String username;
//
//	private String password;
//
//	private String name;
//
//	private boolean isAccountNonExpired;
//
//	private boolean isAccountNonLocked;
//
//	private boolean isCredentialsNonExpired;
//
//	private boolean isEnabled;
//
//	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String password;
	private String username;
	private Set<GrantedAuthority> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

}
