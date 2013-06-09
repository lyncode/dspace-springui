package org.dspace.springui.services.impl.configuration;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;


public class DSpaceConfigurationLocator {
	private static final String DEFAULT_CONFIG_FILE = "dspace.properties";
	private static final String USER_HOME = "${user.home}";
	private static final String CUSTOM_HOME = "${config.dir}";
	private static final String DEFAULT_CONFIG_DIR = ".dspace";

	public static File getConfigurationDirectory(ConfigurableApplicationContext ctx) {
		File result;
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

	public static String getConfigurationFilename() {
		return DEFAULT_CONFIG_FILE;
	}
}
