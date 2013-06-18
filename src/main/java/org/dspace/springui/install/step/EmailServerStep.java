package org.dspace.springui.install.step;

import javax.servlet.http.HttpServletRequest;
import org.dspace.springui.install.model.EmailServerInformation;
import org.dspace.springui.install.model.EmailServerInformation.ConnectionType;
import org.springframework.ui.ModelMap;

public class EmailServerStep extends AbstractStep<EmailServerInformation> {

	@Override
	public void prepare(ModelMap model) {
	}

	@Override
	public EmailServerInformation validate(HttpServletRequest request) throws InstallException {
		String host = request.getParameter("host");
		String testEmail = request.getParameter("test");
		String connection = request.getParameter("connection");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String portI = request.getParameter("port");
		String ignoreButton = request.getParameter("ignore");
		
		
		if (ignoreButton == null) {
			int port = 0;
			try {
				port = Integer.parseInt(portI);
			} catch (Exception e) {
				throw new InstallException("Malforme port number", e);
			}
			EmailServerInformation email = new EmailServerInformation(host, port, username, password);
			email.setConnection(ConnectionType.valueOf(connection.toUpperCase()));
			
			try {
				email.test(testEmail);
				return email;
			} catch (Exception e) {
				throw new InstallException("Unable to send test message", e);
			}
		}
		
		return null;
	}

	@Override
	public void install(Object values) throws InstallException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getView() {
		return "mail-server";
	}

}
