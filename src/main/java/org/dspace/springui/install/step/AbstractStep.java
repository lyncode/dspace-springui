package org.dspace.springui.install.step;

import javax.servlet.http.HttpServletRequest;
import org.dspace.springui.install.model.InstallObject;
import org.springframework.ui.ModelMap;

public abstract class AbstractStep {
	public abstract void prepare (ModelMap model);
	public abstract InstallObject validate (HttpServletRequest request) throws InstallException;
	public abstract void install (Object object) throws InstallException;
	public abstract String getView ();
}
