package org.dspace.springui.web.controller;

import javax.servlet.http.HttpSession;

import org.dspace.springui.orm.DatabaseConnection;
import org.dspace.springui.services.api.email.SMTPSettings;
import org.dspace.springui.services.api.email.SMTPSettings.ConnectionType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/install")
@Controller
public class InstallController {
	
	@RequestMapping("")
	public String indexAction () {
		return "forward:/install/step1";
	}

	@RequestMapping("/step1")
	public String step1Action (ModelMap model) {
		return "install/step1";
	}

	@RequestMapping("/step2")
	public String step2Action (ModelMap model, HttpSession session,
			@RequestParam("host") String host, 
			@RequestParam("username") String username,
			@RequestParam(value="password", required=false, defaultValue="") String password,
			@RequestParam("schema") String schema,
			@RequestParam(value="port", required=false, defaultValue="5432") String portI) {
		int port = 0;
		try {
			port = Integer.parseInt(portI);
		} catch (Exception e) {
			// Do nothing
		}
		DatabaseConnection connection = new DatabaseConnection(host, username, password, schema, port);
		if (connection.isAvailable()) {
			session.setAttribute("database", connection);
		} else {
			model.addAttribute("connection", connection);
			model.addAttribute("error", "Unable to connect to database. Are you providing the correct details?");
			return "install/step1";
		}
		return "install/step2";
	}


	@RequestMapping("/step3")
	public String step3Action (ModelMap model, HttpSession session,
			@RequestParam("host") String host, 
			@RequestParam("test") String test,
			@RequestParam(value="connection", required=false) String connection,
			@RequestParam(value="username", required=false) String username,
			@RequestParam(value="password", required=false) String password,
			@RequestParam(value="port", required=false, defaultValue="25") String portI) {
		int port = 0;
		try {
			port = Integer.parseInt(portI);
		} catch (Exception e) {
			// Do nothing
		}
		SMTPSettings email = new SMTPSettings(host, port, username, password);
		email.setConnection(ConnectionType.valueOf(connection.toUpperCase()));
		
		if (email.isAvailable(test)) {
			session.setAttribute("email", email);
		} else {
			model.addAttribute("test", test);
			model.addAttribute("email", email);
			model.addAttribute("error", "Unable to connect to SMTP server. Are you providing the correct details?");
			return "install/step2";
		}
		return "install/step3";
	}
}
