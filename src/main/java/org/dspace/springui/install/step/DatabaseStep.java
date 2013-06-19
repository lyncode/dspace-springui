package org.dspace.springui.install.step;

import javax.servlet.http.HttpServletRequest;
import org.dspace.springui.install.model.DatabaseInformation;
import org.springframework.ui.ModelMap;

public class DatabaseStep extends AbstractStep {

	@Override
	public void prepare(ModelMap model) {
		
	}

	@Override
	public DatabaseInformation validate(HttpServletRequest request) throws InstallException {
		String portI = request.getParameter("port");
		String host = request.getParameter("host");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String schema = request.getParameter("schema");
		int port = 0;
		try {
			port = Integer.parseInt(portI);
		} catch (Exception e) {
			throw new InstallException("Malformed port number", e);
		}
		DatabaseInformation connection = new DatabaseInformation(host, username, password, schema, port);
		try {
			connection.test();
		} catch (Exception e) {
			throw new InstallException("Unable to connect to database", e);
		}
		return connection;
	}

	@Override
	public void install(Object values) throws InstallException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getView() {
		return "database";
	}
	
}
