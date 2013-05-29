package com.lyncode.dspace.springui.web.invokers;

import java.util.Map;

import com.lyncode.dspace.springui.web.security.User;

public class UserRelated {
	public void getUser (Map<String, Object> model) {
		model.put("user", User.getCurrentUser());
	}
}
