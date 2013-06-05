package com.lyncode.dspace.springui.services.api.configuration;

import java.io.File;

public interface ConfigurationFileResolver {
	static final String DEFAULT_CONFIG_NAME = "default";
	File resolveFile (String name);
	String resolvePropertyFileName (String propertyKey);
}
