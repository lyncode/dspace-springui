package org.dspace.springui.web.invokers;

import java.util.Map;

import org.dspace.springui.web.security.User;


public class UserRelated {
	public void getUser (Map<String, Object> model) {
		model.put("user", User.getCurrentUser());
	}
}
