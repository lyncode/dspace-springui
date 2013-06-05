package com.lyncode.dspace.springui.services.impl.configuration;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationFileResolver;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationService;
import com.lyncode.dspace.springui.services.api.configuration.PropertyWatcherHandler;

public class DSpaceConfigurationService implements ConfigurationService {
	@Autowired ApplicationContext context;
	@Autowired ConfigurationFileResolver fileResolver;
	
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
	
	@Override
	public boolean setProperty(String key, Object value) {
		DSpacePropertySource<?> source = this.getSource(key);
		if (source != null) {
			source.set(key, value);
			return true;
		} else {
			return false;
		}
	}
	
	private DSpacePropertySource<?> getSource (String key) {
		ConfigurableApplicationContext cfg = (ConfigurableApplicationContext) context;
		Iterator<PropertySource<?>> srcs = cfg.getEnvironment().getPropertySources().iterator();
		String fileName = fileResolver.resolvePropertyFileName(key);
		DSpacePropertySource<?> defaultSource = null;
		while (srcs.hasNext()) {
			PropertySource<?> src = srcs.next();
			if (src instanceof DSpacePropertySource) {
				if (((DSpacePropertySource<?>) src).getName().equals(fileName)) {
					return ((DSpacePropertySource<?>) src);
				} else if (((DSpacePropertySource<?>) src).getName().equals(ConfigurationFileResolver.DEFAULT_CONFIG_NAME)) {
					defaultSource = (DSpacePropertySource<?>) src;
				}
			}
		}
		return defaultSource;
	}

	@Override
	public boolean setPropertyWatchHandler(PropertyWatcherHandler handler,
			String key) {
		DSpacePropertySource<?> source = this.getSource(key);
		if (source != null) {
			source.setHandler(key, handler);
			return true;
		}
		return false;
	}

	@Override
	public boolean addProperty(String key, Object value) {
		DSpacePropertySource<?> source = this.getSource(key);
		if (source != null) {
			source.add(key, value);
			return true;
		}
		return false;
	}

}
