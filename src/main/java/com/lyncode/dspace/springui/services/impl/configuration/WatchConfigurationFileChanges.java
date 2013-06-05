package com.lyncode.dspace.springui.services.impl.configuration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class WatchConfigurationFileChanges implements Runnable {
	private static Logger log = LogManager.getLogger(WatchConfigurationFileChanges.class); 
	private File file;
	private boolean changed;
	
	
	public WatchConfigurationFileChanges(File f) {
		this.file = f;
		this.changed = false;
	}
	
	public synchronized boolean isModified () {
		if (this.changed) {
			this.changed = false;
			return true;
		}
		return false;
	}

	public synchronized void setModified () {
		this.changed = true;
	}

	@Override
	public void run() {
		while (true) {
			Path path = Paths.get(this.file.getParentFile().toURI());
			try {
				WatchService watcher = path.getFileSystem().newWatchService();
				path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
						StandardWatchEventKinds.ENTRY_DELETE,
						StandardWatchEventKinds.ENTRY_MODIFY);

				WatchKey watckKey = watcher.take();

				List<WatchEvent<?>> events = watckKey.pollEvents();
				for (WatchEvent<?> event : events) {
					Path file = (Path) event.context();
					if (this.file.equals(file)) 
						this.setModified();
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
