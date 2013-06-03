package com.lyncode.dspace.springui.services.api.context;

import com.lyncode.dspace.springui.orm.entity.Eperson;

public interface Context {
	Eperson getCurrentEperson();
	boolean ignoreAuthorization();
	boolean isAdmin();
}
