package javaserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{
	App testApp;
	ServerFactory mockedServerFactory;
	Server mockedServer;
	static int defaultPort = 5000;

	@BeforeClass
	public void setUp() {
		this.testApp = new App();
		this.mockedServerFactory = spy(new ServerFactory());
		this.mockedServer = mock(Server.class);
	}
	
	public void testDefaultPort() {
		assertEquals(defaultPort, App.PORT);
	}
	
	public void testSetsUpSerer() throws IOException {
		assertEquals(App.server, null);
		App.setUpServer(mockedServerFactory);
		assertTrue(App.server != null);
	}
	
	public void testRunsServer() throws IOException {
		App.runServer(mockedServer);
		verify(mockedServer).run();
	}
	
	public void testTearsDownServer() throws IOException {
		App.runServer(mockedServer);
		verify(mockedServer).tearDown();
	}
}
