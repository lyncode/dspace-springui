package com.lyncode.dspace.springui.web.security;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	private static final long serialVersionUID = -8483700184617009271L;
	private StaticRole role;
	
	public Role () {
		this.role = StaticRole.ADMIN;
	}
	
	public Role (StaticRole role) {
		this.role = role;
	}
	
	public String getAuthority() {
		return "ROLE_"+role.name();
	}

	public enum StaticRole {
		ADMIN
	}
}
