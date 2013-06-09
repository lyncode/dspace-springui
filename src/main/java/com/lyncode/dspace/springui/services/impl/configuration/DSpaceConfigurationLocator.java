package com.lyncode.dspace.springui.services.impl.configuration;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.lyncode.dspace.springui.services.api.configuration.ConfigurationLocator;

public class DSpaceConfigurationLocator implements ConfigurationLocator {
	private static final String DEFAULT_CONFIG_FILE = "dspace.properties";
	private static final String USER_HOME = "${user.home}";
	private static final String CUSTOM_HOME = "${config.dir}";
	private static final String DEFAULT_CONFIG_DIR = ".dspace";
	@Autowired ApplicationContext applicationContext;
	
	public DSpaceConfigurationLocator() {
	}

	@Override
	public File getConfigurationDirectory() {
		File result;
		ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
		String value = ctx.getEnvironment().resolvePlaceholders(CUSTOM_HOME);
		if (StringUtils.isBlank(value) || value.equals(CUSTOM_HOME)) {
			// Create directories
			result = new File(ctx.getEnvironment().resolvePlaceholders(USER_HOME), DEFAULT_CONFIG_DIR);
		} else {
			result = new File(value);
		}
		if (!result.exists())
			result.mkdirs();
		return result;
	}

	@Override
	public String getConfigurationFilename() {
		return DEFAULT_CONFIG_FILE;
	}
}
