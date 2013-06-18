package org.dspace.springui.install.step;

import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;

public abstract class AbstractStep<T> {
	public abstract void prepare (ModelMap model);
	public abstract T validate (HttpServletRequest request) throws InstallException;
	public abstract void install (Object object) throws InstallException;
	public abstract String getView ();
}
