package org.dspace.springui.services.api.email;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SMTPSettings {
	private String host;
	private int port;
	private boolean hasAuthentication;
	
	private String username;
	private String password;
	

	public SMTPSettings(String host, int port) {
		this.host = host;
		this.port = port;
		this.hasAuthentication = false;
	}
	
	public SMTPSettings (String host, int port, String user, String pass) {
		this.host = host;
		this.port = port;
		
		this.hasAuthentication = (user != null);
		
		this.username = user;
		this.password = pass;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the hasAuthentication
	 */
	public boolean hasAuthentication() {
		return hasAuthentication;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	
	public boolean isAvailable () {
		Properties props = new Properties();
        // required for gmail 
        props.put("mail.smtp.auth", (this.hasAuthentication() ? "true" : "false"));
        // or use getDefaultInstance instance if desired...
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setPort(this.getPort());
        sender.setHost(this.getHost());
        sender.setJavaMailProperties(props);
        if (this.hasAuthentication()) {
        	sender.setUsername(this.getUsername());
        	sender.setPassword(this.getPassword());
        }
        return true;
	}

}
