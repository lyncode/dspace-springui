package com.lyncode.dspace.springui.services.impl.context;

import com.lyncode.dspace.springui.orm.entity.Eperson;
import com.lyncode.dspace.springui.services.api.context.Context;
import com.lyncode.dspace.springui.services.api.security.User;
import com.lyncode.dspace.springui.web.security.Role.StaticRole;

public class DSpaceContext implements Context {
	private boolean ignoreAuthorization;
	private User user;
	
	public DSpaceContext (User usr) {
		this.user = usr;
	}
	
	@Override
	public Eperson getCurrentEperson() {
		return this.user.getEperson();
	}

	@Override
	public boolean ignoreAuthorization() {
		return this.ignoreAuthorization;
	}

	@Override
	public boolean isAdmin() {
		return this.user.getAuthorities().contains(StaticRole.ADMIN);
	}
	
}
