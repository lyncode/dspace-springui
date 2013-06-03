package com.lyncode.dspace.springui.web.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.lyncode.dspace.springui.web.security.Role.StaticRole;

public class User implements Authentication, Serializable {
	private static final long serialVersionUID = 4466828947980026611L;

	public static User getCurrentUser () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) 
			return (User) auth;
		else
			return guestInstance();
	}
	
	public static User guestInstance () {
		return new User();
	}
	public static User adminInstance () {
		return new User(true);
	}
	public static User memberInstance () {
		return new User(false);
	}
	
	private boolean guest;
	private boolean admin;
	
	public User () {
		guest = true;
	}
	
	public User(boolean admin) {
		this.guest = false;
		this.admin = admin;
	}

	public boolean isGuest() {
		return guest;
	}
	
	public boolean isAdmin () {
		return admin;
	}
	
	public boolean equals (Object obj) {
		if (obj instanceof User) {
			User u = (User) obj;
			if (!u.isGuest() && !this.isGuest()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return (this.guest) ? "Guest" : "Administrator";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = new ArrayList<Role>();
		if (!this.isGuest()) {
			roles.add(new Role(StaticRole.ADMIN));
		}
		return roles;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.getName();
	}

	@Override
	public boolean isAuthenticated() {
		return !this.isGuest();
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		this.guest = !arg0;
	}
}
