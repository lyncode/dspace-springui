package org.dspace.springui.web.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dspace.springui.core.Initializer;
import org.dspace.springui.services.api.configuration.ConfigurationServiceException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class WebInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
	private static Logger log = LogManager.getLogger(WebInitializer.class);

	@Override
	public void initialize(ConfigurableWebApplicationContext context) {
		try {
			Initializer.initialize((ConfigurableApplicationContext)context);
		} catch (ConfigurationServiceException e) {
			log.error("Unable to initialize configurations", e);
		}
	}

}
