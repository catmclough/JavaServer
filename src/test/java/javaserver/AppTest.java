package javaserver;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{
	App testApp;
	private ServerSocket socket;
	private RequestParser parser;
	private Responder responder;
	private MockServer mockServer;
	static int defaultPort = 5000;

	@BeforeClass
	public void setUp() throws IOException {
		this.testApp = new App();
		this.socket = new ServerSocket();
		this.parser = new RequestParser();
		this.responder = new Responder();
		this.mockServer = new MockServer(socket, parser, responder);
	}

	@After
	public void shutDown() throws IOException {
		socket.close();
	}

	public void testDefaultPort() {
		assertEquals(defaultPort, App.PORT);
	}

	public void testSetsUpSerer() throws IOException {
		assertNull(App.server);
		App.setUpServer(new ServerFactory());
		assertNotNull(App.server);
	}

	public void testRunsServer() throws IOException {
		App.runServer(mockServer);
		assertTrue(mockServer.isRunning);
	}
}

class MockServer extends Server {
	public boolean isRunning;

	MockServer(ServerSocket socket, RequestParser parser, Responder responder) {
		super(socket, parser, responder);
	}

	@Override
	public void run() {
		this.isRunning = true;
	}
}

