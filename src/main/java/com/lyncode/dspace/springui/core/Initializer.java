package com.lyncode.dspace.springui.core;

import java.io.File;

import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationLocator;
import com.lyncode.dspace.springui.services.api.configuration.ConfigurationServiceException;
import com.lyncode.dspace.springui.services.impl.configuration.DSpacePropertySource;
import com.lyncode.dspace.springui.web.listener.configuration.ReloadStrategy;
import com.lyncode.dspace.springui.web.listener.configuration.WatchConfigurationFileChanges;

public class Initializer {
	private static Thread fileWatcher = null;
	
	public static void initialize (ApplicationContext context) throws ConfigurationServiceException {
		if (fileWatcher == null) {
			ConfigurationLocator configLocator = context.getBean(ConfigurationLocator.class);
			WatchConfigurationFileChanges watcher = new WatchConfigurationFileChanges(configLocator);
			ReloadingStrategy reloader = new ReloadStrategy(watcher);
			File configFile = new File(configLocator.getConfigurationDirectory(), configLocator.getConfigurationFilename());
			DSpacePropertySource source = new DSpacePropertySource(configFile, reloader);
			ConfigurableApplicationContext cfg = (ConfigurableApplicationContext) context;
			cfg.getEnvironment().getPropertySources().addFirst(source);
			fileWatcher.start();
		}
	}
	
	public static void destroy () {
		if (fileWatcher != null && fileWatcher.isAlive())
			fileWatcher.interrupt();
	}
}
