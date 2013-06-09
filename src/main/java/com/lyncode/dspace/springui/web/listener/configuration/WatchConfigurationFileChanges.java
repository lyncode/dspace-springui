package com.lyncode.dspace.springui.web.listener.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationLocator;

public class WatchConfigurationFileChanges implements Runnable {
	private Logger log = LogManager.getLogger(WatchConfigurationFileChanges.class); 
	private ConfigurationLocator locator;
	private boolean changed;
	
	
	public WatchConfigurationFileChanges(ConfigurationLocator loc) {
		locator = loc;
	}
	
	public synchronized boolean isModifiedAndReset () {
		if (this.changed) {
			this.changed = false;
			return this.changed;
		}
		return false;
	}
	public synchronized boolean isModified () {
		return this.changed;
	}

	public synchronized void setModified () {
		this.changed = true;
	}

	@Override
	public void run() {
		while (true) {
			Path path = Paths.get(this.locator.getConfigurationDirectory().toURI());
			try {
				WatchService watcher = path.getFileSystem().newWatchService();
				path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);

				WatchKey watckKey = watcher.take();

				List<WatchEvent<?>> events = watckKey.pollEvents();
				for (WatchEvent<?> event : events) {
					Path file = (Path) event.context();
					if (file != null && 
							file.getFileName() != null && 
							file.getFileName().equals(this.locator.getConfigurationFilename()))
						this.setModified();
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}