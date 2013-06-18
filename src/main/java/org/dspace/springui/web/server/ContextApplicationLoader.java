package org.dspace.springui.web.server;

import javax.servlet.ServletContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

public class ContextApplicationLoader  extends ContextLoaderListener {
	public static final String PARENT_APPLICATION_CONTEXT = "parent-application-context";
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoader#loadParentContext(javax.servlet.ServletContext)
	 */
	@Override
	protected ApplicationContext loadParentContext(ServletContext servletContext) {
		return (ApplicationContext) servletContext.getAttribute(PARENT_APPLICATION_CONTEXT);
	}
	
	
}
