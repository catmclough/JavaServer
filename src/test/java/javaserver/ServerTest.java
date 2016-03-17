package javaserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ServerTest extends TestCase {
	private Server testServer;
	private ServerSocket mockedServerSocket;
	private MockThreadManager mockThreadManager;
	Socket mockedClientSocket;

	int defaultPort = 6000;

	@Before
	public void setUp() throws Exception {
		mockedServerSocket = new MockServerSocket(defaultPort);
		mockThreadManager = new MockThreadManager();
		testServer = new Server(mockedServerSocket, mockThreadManager);
	}

	@After
	public void tearDown() throws IOException {
		testServer.shutDown();
	}

	@Test
	public void testAcceptsClient() throws IOException {
		testServer.run();
		assertEquals(mockedClientSocket.getChannel() ,mockedServerSocket.getChannel());
	}

	@Test
	public void testSetsReader() throws IOException {
		assertNull("Reader should be null before accepting a client", testServer.reader);
		testServer.run();
		assertNotNull("Reader was not properly created", testServer.reader);
	}

	@Test
	public void testSetsWriter() throws IOException {
		assertNull("Writer should be null before accepting a client", testServer.writer);
		testServer.run();
		assertNotNull("Writer was not properly created", testServer.writer);
	}

	@Test
	public void testOpensNewThread() throws IOException {
		testServer.run();
		assertTrue(mockThreadManager.getOpenedThreads() > 0);
	}
	

	@Test
	public void testCanCreateMultipleThreads() throws IOException {
		testServer.run();
		testServer.run();
		testServer.run();
		assertEquals(mockThreadManager.getOpenedThreads(), 3);
	}

	@Test
	public void testServerCanBeShutDown() {
		boolean caughtError = false;
		try {
			testServer.shutDown();
		} catch (IOException e) {
			caughtError = true;
		}
		assertFalse("Server's ServerSocket failed to close", caughtError);
	}

	class MockServerSocket extends ServerSocket {
		private int port;

		MockServerSocket(int port) throws IOException {
		  super(port);
		  this.port = port;
		}

		@Override
		public Socket accept() throws IOException {
		  mockedClientSocket = new Socket("localhost", port);
		  return mockedClientSocket;
		}
	}
	
	class MockSocketWriter extends SocketWriter {
		MockSocketWriter(DataOutputStream mockOutputStream) {
		 	super(mockOutputStream);
	 	}

	 	@Override
	 	public void respond(String response) {
	 		this.latestResponse = response;
	 	}
	}

	class MockThreadManager extends ThreadManager {
		int openedThreads = 0;

		MockThreadManager() {
			super();
		}
		
		@Override
		public void openNewThread(Reader reader, SocketWriter writer) {
			openedThreads++;
		}
		
		public int getOpenedThreads() {
			return this.openedThreads;
		}
	}
}
