package com.lyncode.dspace.springui.web.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UsernamePasswordAuthenticationProvider implements
		AuthenticationProvider {
	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		if (authentication instanceof UsernamePasswordRequest) {
			return this.auth((UsernamePasswordRequest) authentication);
		} else {
			throw new AuthenticationException(null) {
				private static final long serialVersionUID = 2836852428130953842L;
			};
		}
	}

	private Authentication auth(UsernamePasswordRequest authentication) {
		throw new BadCredentialsException("Not implemented yet");
	}

	public boolean supports(Class<?> authentication) {
		return (authentication.isAssignableFrom(UsernamePasswordRequest.class));
	}
}
