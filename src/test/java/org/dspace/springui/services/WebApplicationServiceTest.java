package org.dspace.springui.services;

import static org.junit.Assert.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.dspace.springui.services.impl.application.WebApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext.xml"})
public class WebApplicationServiceTest {

	@Autowired WebApplicationService webService;
	
	@Test
	public void testWebapplicationService() {
		try {
			webService.init();
			webService.start();
			assertTrue(webService.isRunning());
			// Wait?
			Thread.sleep(10000);
			URLConnection conn;
			conn = new URL("http://localhost:"+WebApplicationService.DEFAULT_HTTP_PORT).openConnection();
			conn.connect();
			InputStream input = conn.getInputStream();
			assertNotNull(input);
			input.close();
			webService.stop();
			Thread.sleep(2000);
			assertFalse(webService.isRunning());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
