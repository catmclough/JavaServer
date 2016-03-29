package javaserver;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{

	private App testApp;
	private ServerSocket socket;
	private MockServer mockServer;
	private static int defaultPort = 5000;

	@BeforeClass
	public void setUp() throws IOException {
		this.testApp = new App();
		this.socket = new ServerSocket();
		this.mockServer = new MockServer(socket);
	}

	public void testDefaultPort() {
		assertEquals(defaultPort, App.PORT);
	}

	public void testServerCreation() throws IOException {
		App.setUpServer();
		assertNotNull(App.server);
	}

	public void testRunsServer() throws IOException {
		App.runServer(mockServer);
		assertTrue(mockServer.isRunning);
	}

	public void testAppCanShutDownServer() throws IOException {
		App.runServer(mockServer);
		assertFalse(App.isOn);
	}
}

class MockServer extends Server {
	public boolean isRunning;

	MockServer(ServerSocket socket) {
		super(socket, new ThreadManager());
	}

	@Override
	public void run() {
		this.isRunning = true;
		App.close();
	}
}
