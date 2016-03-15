package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ServerTest extends TestCase {
	private MockServerFactory mockedServerFactory;
	private Server testServer;
	private ServerSocket mockedServerSocket;
	Socket mockedClientSocket;

	int defaultPort = 6000;

	@Before
	public void setUp() throws Exception {
		mockedServerFactory = new MockServerFactory();
		mockedServerSocket = new MockServerSocket(defaultPort);
		testServer = new Server(mockedServerFactory, mockedServerSocket, new RequestBuilder(), new Responder(new ResponseBuilder()));
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
	public void testCreatesMultipleThreads() throws IOException {
		testServer.run();
		testServer.run();
		testServer.run();
		assertEquals(mockedServerFactory.getNumThreads(), 3);
	}

	@Test
	public void testStartsThread() throws IOException {
		testServer.run();
		assertEquals(true, mockedServerFactory.mockedWorker.threadStarted);
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

	class MockServerFactory extends ServerFactory {
		public MockClientWorker mockedWorker;
		private int threadsCreated;

		MockServerFactory() {
			super();
		}

		@Override
		public ClientWorker createClientWorker(Reader reader, RequestBuilder requestBuilder, Responder responder, SocketWriter writer) {
			this.threadsCreated++;
			this.mockedWorker = new MockClientWorker(reader, requestBuilder, responder, writer);
			return mockedWorker;
		}

		public int getNumThreads() {
			return this.threadsCreated;
		}
	}

	class MockClientWorker extends ClientWorker {
		public boolean threadStarted;

		MockClientWorker(Reader reader, RequestBuilder requestBuilder, Responder responder, SocketWriter writer) {
			super(reader, requestBuilder, responder, writer);
		}

		@Override
		public void run(){
			this.threadStarted = true;
		}
	}
}

