package org.dspace.springui.app;

import java.util.Map;
import org.apache.log4j.Logger;
import org.dspace.springui.services.api.application.Service;
import org.dspace.springui.services.api.application.ServiceException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
	private static Logger log = Logger.getLogger(Starter.class);
	public static void main(String[] args) {
		try {
			final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
			final Map<String, Service> services = applicationContext.getBeansOfType(Service.class);
			
			// Initialization phase
			for (Service service : services.values()) {
				applicationContext.getAutowireCapableBeanFactory().autowireBean(service);
				service.init();
			}
			
			// Start phase
			for (Service service : services.values())
				service.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread()
	        {
	            @Override
	            public void run()
	            {
	            	for (Service service : services.values()) {
						try {
							service.destroy();
						} catch (ServiceException e) {
							log.error("Error destroying service '"+service.getName()+"'", e);
						}
	            	}
	            	
	            	applicationContext.close();
	            }
	        });
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}
	}
}
