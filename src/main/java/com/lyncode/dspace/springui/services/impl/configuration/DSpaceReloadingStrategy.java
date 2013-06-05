package com.lyncode.dspace.springui.services.impl.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.reloading.ReloadingStrategy;

public class DSpaceReloadingStrategy implements ReloadingStrategy {
	private static final String JAR_PROTOCOL = "jar";
	private FileConfiguration configuration;
	private WatchConfigurationFileChanges changes;

	public DSpaceReloadingStrategy() {

	}

	@Override
	public void init() {
		this.changes = new WatchConfigurationFileChanges(getFile());
	}

	@Override
	public void reloadingPerformed() {
		// Don't do anythinga
	}

	@Override
	public boolean reloadingRequired() {
		return this.changes.isModified();
	}

	@Override
	public void setConfiguration(FileConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Returns the file that is monitored by this strategy. Note that the return
	 * value can be <b>null </b> under some circumstances.
	 * 
	 * @return the monitored file
	 */
	private File getFile() {
		return (configuration.getURL() != null) ? fileFromURL(configuration
				.getURL()) : configuration.getFile();
	}

	/**
	 * Helper method for transforming a URL into a file object. This method
	 * handles file: and jar: URLs.
	 * 
	 * @param url
	 *            the URL to be converted
	 * @return the resulting file or <b>null </b>
	 */
	private File fileFromURL(URL url) {
		if (JAR_PROTOCOL.equals(url.getProtocol())) {
			String path = url.getPath();
			try {
				return ConfigurationUtils.fileFromURL(new URL(path.substring(0,
						path.indexOf('!'))));
			} catch (MalformedURLException mex) {
				return null;
			}
		} else {
			return ConfigurationUtils.fileFromURL(url);
		}
	}

	public Thread getWatcherThread() {
		return new Thread(this.changes);
	}
}
