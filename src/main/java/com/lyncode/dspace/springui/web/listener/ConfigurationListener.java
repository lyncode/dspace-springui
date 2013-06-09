package com.lyncode.dspace.springui.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lyncode.dspace.springui.core.Initializer;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationServiceException;

public class ConfigurationListener implements ServletContextListener  {
	private static Logger log = LogManager.getLogger(ConfigurationListener.class);
	public ConfigurationListener() {}
	

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Initializer.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ApplicationContext context = WebApplicationContextUtils
        	.getRequiredWebApplicationContext(event.getServletContext());
		
		try {
			Initializer.initialize(context);
		} catch (ConfigurationServiceException e) {
			log.error("Unable to initialize configurations", e);
		}
	}

	
}
