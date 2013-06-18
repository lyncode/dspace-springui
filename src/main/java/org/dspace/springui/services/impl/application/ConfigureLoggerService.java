package org.dspace.springui.services.impl.application;

import org.apache.log4j.xml.DOMConfigurator;
import org.dspace.springui.services.api.application.Service;
import org.dspace.springui.services.api.application.ServiceException;
import org.dspace.springui.util.DirectoryUtils;

public class ConfigureLoggerService implements Service {
	private static final String CONFIG = DirectoryUtils.getPathWithParent("config", "log4j.xml");
	

	@Override
	public void refresh() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws ServiceException {
		DOMConfigurator.configure(CONFIG);
	}

	@Override
	public void destroy() throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
