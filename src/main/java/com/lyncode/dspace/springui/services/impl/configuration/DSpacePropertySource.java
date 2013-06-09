package com.lyncode.dspace.springui.services.impl.configuration;

import java.io.File;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.env.PropertySource;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationPropertyChangeHandler;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationServiceException;

public class DSpacePropertySource extends PropertySource<Object> {
	public static final String DSPACE_SOURCE = "dspaceSource";
	private static Logger log = LogManager.getLogger(DSpacePropertySource.class);
	private PropertiesConfiguration properties;
	
	public DSpacePropertySource(File f, ReloadingStrategy reloader) throws ConfigurationServiceException {
		super(f.getName());
		try {
			properties = new PropertiesConfiguration(f);
			properties.setAutoSave(true);
			properties.setReloadingStrategy(reloader);
			properties.setLogger(new Log4JLogger(log));
		} catch (ConfigurationException e) {
			throw new ConfigurationServiceException(e);
		}
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

	public void setHandler(String name, ConfigurationPropertyChangeHandler handler) {
		this.properties.addConfigurationListener(new DSpaceConfigurationListener(name, handler));
	}

	public void add(String key, Object value) {
		this.properties.addProperty(key, value);
	}

	public class DSpaceConfigurationListener implements ConfigurationListener {
		private String key;
		private ConfigurationPropertyChangeHandler handler;
		
		
		public DSpaceConfigurationListener(String key, ConfigurationPropertyChangeHandler handler) {
			this.key = key;
			this.handler = handler;
		}

		@Override
		public void configurationChanged(ConfigurationEvent event) {
			if (event.getType() == AbstractConfiguration.EVENT_ADD_PROPERTY) {
				if (event.getPropertyName().equals(this.key)) {
					this.handler.handleCreation(event.getPropertyValue());
				}
			} else if (event.getType() == AbstractConfiguration.EVENT_SET_PROPERTY) {
				if (event.getPropertyName().equals(this.key)) {
					this.handler.handleModification(event.getPropertyValue());
				}
			} else if (event.getType() == AbstractConfiguration.EVENT_CLEAR_PROPERTY) {
				if (event.getPropertyName().equals(this.key)) {
					this.handler.handleDelete();
				}
			} else if (event.getType() == AbstractConfiguration.EVENT_CLEAR) {
				this.handler.handleDelete();
			}
		}

	}
}
