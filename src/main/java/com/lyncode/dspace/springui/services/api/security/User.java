package com.lyncode.dspace.springui.services.api.security;

import org.springframework.security.core.Authentication;

import com.lyncode.dspace.springui.orm.entity.Eperson;

public interface User extends Authentication {
	Eperson getEperson ();
}
