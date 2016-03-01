package javaserver;

import java.io.IOException;

import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{
	App testApp;
	static int defaultPort = 5000;

	@BeforeClass
	public void setUp() {
		this.testApp = new App();
	}

	public void testDefaultPort() {
		assertEquals(defaultPort, App.PORT);
	}

	public void testSetsUpSerer() throws IOException {
		assertEquals(App.server, null);
	}

	public void testRunsServer() throws IOException {
	}
}

