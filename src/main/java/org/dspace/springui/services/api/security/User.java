package org.dspace.springui.services.api.security;

import org.dspace.springui.orm.entity.Eperson;
import org.springframework.security.core.Authentication;


public interface User extends Authentication {
	Eperson getEperson ();
}
