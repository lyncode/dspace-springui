package com.lyncode.dspace.springui.services.impl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationService;

public class DSpaceConfigurationService implements ConfigurationService {
	@Autowired ApplicationContext context;
	
	@Override
	public <T> T getProperty(String name, Class<T> type) {
		return context.getEnvironment().getProperty(name, type);
	}

	@Override
	public String getProperty(String name) {
		return context.getEnvironment().getProperty(name);
	}

	@Override
	public <T> T getProperty(String name, Class<T> type, T defaultValue) {
		return context.getEnvironment().getProperty(name, type, defaultValue);
	}

}
