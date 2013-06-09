package com.lyncode.dspace.springui.services.api.configuration;

import java.io.File;

public interface ConfigurationLocator {
	File getConfigurationDirectory();
	String getConfigurationFilename ();
}
