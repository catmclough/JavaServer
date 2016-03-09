package javaserver;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.BeforeClass;

import junit.framework.TestCase;

public class AppTest extends TestCase{
	App testApp;
	private ServerFactory factory;
	private ServerSocket socket;
	private RequestBuilder requestBuilder;
	private Responder responder;
	private MockServer mockServer;
	static int defaultPort = 5000;

	@BeforeClass
	public void setUp() throws IOException {
		this.factory = new ServerFactory();
		this.testApp = new App();
		this.socket = new ServerSocket();
		this.requestBuilder = new RequestBuilder();
		this.responder = new Responder();
		this.mockServer = new MockServer(factory, socket, requestBuilder, responder);
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

	MockServer(ServerFactory factory, ServerSocket socket, RequestBuilder requestBuilder, Responder responder) {
		super(factory, socket, requestBuilder, responder);
	}

	@Override
	public void run() {
		this.isRunning = true;
		App.close();
	}
}

