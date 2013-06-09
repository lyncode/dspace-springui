package org.dspace.springui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/install")
@Controller
public class InstallController {
	
	@RequestMapping("")
	public String indexAction () {
		return "forward:/install/step1";
	}

	@RequestMapping("/step1")
	public String step1Action () {
		return "install/step1";
	}

	@RequestMapping("/step2")
	public String step2Action () {
		return "install/step2";
	}

}
