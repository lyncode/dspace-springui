package org.dspace.springui.services.impl.application;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.springui.services.api.application.Service;
import org.dspace.springui.services.api.application.ServiceException;
import org.dspace.springui.services.api.configuration.ConfigurationPropertyChangeHandler;
import org.dspace.springui.services.api.configuration.ConfigurationService;
import org.eclipse.jetty.ajp.Ajp13SocketConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.annotation.Autowired;

public class WebApplicationService implements Service {
	private static final String ROOT_HANDLER_NAME = "ROOT";
	private static final String WAR_EXTENSION = ".war";
	private static Logger log = Logger.getLogger(WebApplicationService.class);
	@Autowired ConfigurationService config;
	
	private Server server;
	private Map<String, Handler> handlers;
	private Map<String, StateRestartServiceHandler> watchHandlers;

	@Override
	public synchronized void refresh() throws ServiceException {
		this.stop();
		this.start();
	}
	
	public synchronized void refresh (String handlerName) throws ServiceException {
		if (handlers == null) throw new ServiceException("Service not initialized correctly");
		Handler handler = handlers.get(handlerName);
		if (handler != null) {
			try {
				handler.stop();
				handler.start();
			} catch (Exception e) {
				throw new ServiceException("Unable to reload module '"+handlerName+"'", e);
			}
		}
	}
	
	public String getName () {
		return "Web Application";
	}

	@Override
	public synchronized void start() throws ServiceException {
		if (this.server == null)
			throw new ServiceException("Service not correctly initialized");

		try {
			if (server.isRunning())
				server.stop();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		HandlerList list = new HandlerList();
		for (String name : handlers.keySet())
			if (this.isWebappActive(name)) 
				list.addHandler(handlers.get(name));
		
		server.setHandler(list);
        
        try {
			server.start();
			log.info("Web server started");
	        server.join();
		} catch (Exception e) {
			throw new ServiceException("Unable to start web server", e);
		}
	}

	@Override
	public synchronized void stop() throws ServiceException {
		try {
			this.server.stop();
		} catch (Exception e) {
			throw new ServiceException("Unable to stop web server", e);
		}
	}

	private boolean isWebappActive (String name) {
		if (name.equals(ROOT_HANDLER_NAME))
			return this.config.getProperty(this.getWebappActiveProperty(name), Boolean.class, true);
		else
			return this.config.getProperty(this.getWebappActiveProperty(name), Boolean.class, false);
	}
	
	public boolean isRunning () {
		if (this.server != null)
			return this.server.isRunning();
		return false;
	}
	
	private String getWebappActiveProperty (String name) {
		String propName = "webapp."+name.toLowerCase()+".active";
		if (name.equals(ROOT_HANDLER_NAME))
			propName = "webapp.main.active";
		
		return propName;
	}
	
	public void init () {
		this.server = new Server();
		List<Connector> connectors = new ArrayList<Connector>();
	
		if (config.getProperty("server.http", Boolean.class, true)) {
			SocketConnector connectorHTTP = new SocketConnector();
			connectorHTTP.setPort(config.getProperty("server.http.port", Integer.class));
			connectors.add(connectorHTTP);
		}
		
		if (config.getProperty("server.ajp", Boolean.class, false)) {
			Ajp13SocketConnector ajp = new Ajp13SocketConnector();
			ajp.setPort(config.getProperty("server.ajp.port", Integer.class));
		}
 
		// register the connector
		server.setConnectors((Connector[]) connectors.toArray());
		File webappDir = new File(config.getProperty("server.webapps"));
		
		handlers = new HashMap<String, Handler>();
		File[] files = webappDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return dir.isFile() && dir.getName().toLowerCase().endsWith(WAR_EXTENSION);
			}
		});
		
		watchHandlers = new HashMap<String, StateRestartServiceHandler>();
		
		for (File war : files) {
			String name = war.getName().toLowerCase().replace(WAR_EXTENSION, "");
			WebAppContext webapp = new WebAppContext();
			if (name.equals(ROOT_HANDLER_NAME))
				webapp.setContextPath("/");
			else
				webapp.setContextPath(name);
			
			webapp.setWar(war.getPath());
			webapp.setParentLoaderPriority(true);
			
			handlers.put(name, webapp);
			
			StateRestartServiceHandler watcher = new StateRestartServiceHandler(this, this.isWebappActive(name));
			config.addWatchHandler(watcher, this.getWebappActiveProperty(name));
			watchHandlers.put(name, watcher);
		}
	}


	@Override
	public void destroy() throws ServiceException {
		this.stop();
		for (StateRestartServiceHandler watcher : this.watchHandlers.values()) 
			this.config.removeWatchHandler(watcher);
	}
	
	public class StateRestartServiceHandler extends ConfigurationPropertyChangeHandler {
		private boolean active;
		private WebApplicationService service;
		
		public StateRestartServiceHandler (WebApplicationService service, boolean active) {
			this.active = active;
			this.service = service;
		}
		
		@Override
		public void handleModification(Object object) {
			if (this.active != (Boolean) object) {
				this.active = (Boolean) object;
				try {
					if (service.isRunning())
						this.service.refresh();
				} catch (ServiceException e) {
					log.error(e.getMessage(), e);
				}
			}
		}

		@Override
		public void handleCreation(Object object) {
			try {
				if (service.isRunning())
					this.service.refresh();
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
		}

		@Override
		public void handleDelete() {
			try {
				if (service.isRunning())
					this.service.refresh();
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
