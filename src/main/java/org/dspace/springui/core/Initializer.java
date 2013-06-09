package org.dspace.springui.core;

import java.io.File;

import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.dspace.springui.services.api.configuration.ConfigurationServiceException;
import org.dspace.springui.services.impl.configuration.DSpaceConfigurationLocator;
import org.dspace.springui.services.impl.configuration.DSpacePropertySource;
import org.dspace.springui.web.core.configuration.ReloadStrategy;
import org.dspace.springui.web.core.configuration.WatchConfigurationFileChanges;
import org.springframework.context.ConfigurableApplicationContext;


public class Initializer {
	private static Thread fileWatcher = null;
	
	public static void initialize (ConfigurableApplicationContext context) throws ConfigurationServiceException {
		if (fileWatcher == null) {
			WatchConfigurationFileChanges watcher = new WatchConfigurationFileChanges();
			ReloadingStrategy reloader = new ReloadStrategy(watcher);
			File configFile = new File(DSpaceConfigurationLocator.getConfigurationDirectory(context), DSpaceConfigurationLocator.getConfigurationFilename());
			DSpacePropertySource source = new DSpacePropertySource(configFile, reloader);
			context.getEnvironment().getPropertySources().addFirst(source);
			fileWatcher.start();
		}
	}
	
	public static void destroy () {
		if (fileWatcher != null && fileWatcher.isAlive())
			fileWatcher.interrupt();
	}
}
