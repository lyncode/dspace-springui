package com.lyncode.dspace.springui.services.impl.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import com.lyncode.dspace.springui.orm.dao.api.IEpersonDao;
import com.lyncode.dspace.springui.orm.entity.Eperson;
import com.lyncode.dspace.springui.services.api.security.authentication.AuthenticationRequest;
import com.lyncode.dspace.springui.services.api.security.authentication.AuthenticationResponse;
import com.lyncode.dspace.springui.services.api.security.authentication.CannotLoginException;
import com.lyncode.dspace.springui.services.api.security.authentication.GenericAuthenticationProvider;
import com.lyncode.dspace.springui.services.api.security.authentication.RequireCertificateException;
import com.lyncode.dspace.springui.services.api.security.authentication.UnsupportedAuthenticationRequestException;


public class UsernamePasswordAuthenticationProvider extends GenericAuthenticationProvider {
	private static final String INVALID_CREDENTIALS = "Invalid username/password";
	private static final String CANNOT_LOGIN = "User cannot login";
	private static final String REQUIRE_CERTIFICATE = "User login requires certificate";
	private static final String UNSUPPORTED = "Unsupported authentication request";
	@Autowired IEpersonDao personDao;

	private UsernamePasswordAuthenticationResponse auth(UsernamePasswordRequest authentication) {
		if (authentication.getEmail() != null && authentication.getPassword() != null) {
			Eperson eperson = personDao.selectByEmail(authentication.getEmail());
			if (eperson != null) {
				if (!eperson.getCanLogIn()) throw new CannotLoginException(CANNOT_LOGIN);
				if (eperson.getRequireCertificate()) throw new RequireCertificateException(REQUIRE_CERTIFICATE);
				if (!eperson.checkPassword(authentication.getPassword())) throw new BadCredentialsException(INVALID_CREDENTIALS);
				return new UsernamePasswordAuthenticationResponse();
			} else throw new BadCredentialsException(INVALID_CREDENTIALS);
		}
		throw new BadCredentialsException(INVALID_CREDENTIALS);
	}

	@Override
	public AuthenticationResponse auth(AuthenticationRequest request) {
		if (request instanceof UsernamePasswordRequest) {
			return this.auth((UsernamePasswordRequest) request);
		} else {
			throw new UnsupportedAuthenticationRequestException(UNSUPPORTED);
		}
	}

	@Override
	public boolean supportsRequest(Class<? super AuthenticationRequest> request) {
		return (request.isAssignableFrom(UsernamePasswordRequest.class));
	}
}
