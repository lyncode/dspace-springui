package com.lyncode.dspace.springui.services.impl.security.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

import com.lyncode.dspace.springui.services.api.security.authentication.AuthenticationRequest;
import com.lyncode.dspace.springui.services.api.security.authentication.AuthenticationResponse;
import com.lyncode.dspace.springui.services.api.security.authentication.GenericAuthenticationProvider;


public class UsernamePasswordAuthenticationProvider extends GenericAuthenticationProvider {


	private UsernamePasswordAuthenticationResponse auth(UsernamePasswordRequest authentication) {
		throw new BadCredentialsException("Not implemented yet");
	}

	@Override
	public AuthenticationResponse auth(AuthenticationRequest request) {
		if (request instanceof UsernamePasswordRequest) {
			return this.auth((UsernamePasswordRequest) request);
		} else {
			throw new AuthenticationException(null) {
				private static final long serialVersionUID = 2836852428130953842L;
			};
		}
	}

	@Override
	public boolean supportsRequest(Class<? super AuthenticationRequest> request) {
		return (request.isAssignableFrom(UsernamePasswordRequest.class));
	}
}
