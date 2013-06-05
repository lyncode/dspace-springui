package com.lyncode.dspace.springui.services.impl.configuration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;

import com.lyncode.dspace.springui.services.api.configuration.PropertyWatcherHandler;

public class DSpaceConfigurationListener implements ConfigurationListener {
	private String key;
	private PropertyWatcherHandler handler;
	
	
	public DSpaceConfigurationListener(String key, PropertyWatcherHandler handler) {
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
