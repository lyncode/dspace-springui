package com.lyncode.dspace.springui.services.impl.configuration;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.env.PropertySource;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationFileResolver;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationServiceException;
import com.lyncode.dspace.springui.services.api.configuration.PropertyWatcherHandler;

public class DSpacePropertySource<T> extends PropertySource<T> {
	private static Logger log = LogManager.getLogger(DSpacePropertySource.class);
	private PropertiesConfiguration properties;
	private Thread thread;
	
	public DSpacePropertySource(String name, ConfigurationFileResolver resolver) throws ConfigurationServiceException {
		super(name);
		try {
			File f = resolver.resolveFile(name);
			DSpaceReloadingStrategy strategy = new DSpaceReloadingStrategy();
			properties = new PropertiesConfiguration(f);
			properties.setAutoSave(true);
			properties.setReloadingStrategy(strategy);
			properties.setLogger(new Log4JLogger(log));
			thread = strategy.getWatcherThread();
		} catch (ConfigurationException e) {
			throw new ConfigurationServiceException(e);
		}
	}
	
	public Thread getWatcherThread() {
		return this.thread;
	}

	@Override
	public Object getProperty(String key) {
		return this.properties.getProperty(key);
	}
	
	public Object get(String key) {
		return this.properties.getProperty(key);
	}

	public boolean has(String name) {
		return this.properties.containsKey(name);
	}

	public void set(String name, Object value) {
		this.properties.setProperty(name, value);
	}

	public void setHandler(String name, PropertyWatcherHandler handler) {
		this.properties.addConfigurationListener(new DSpaceConfigurationListener(name, handler));
	}

	public void add(String key, Object value) {
		this.properties.addProperty(key, value);
	}

}
