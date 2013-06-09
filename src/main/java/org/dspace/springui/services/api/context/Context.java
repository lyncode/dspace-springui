package org.dspace.springui.services.api.context;

import org.dspace.springui.orm.entity.Eperson;

public interface Context {
	Eperson getCurrentEperson();
	boolean ignoreAuthorization();
	boolean isAdmin();
}
