package javaserver;

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
	Socket mockedClientSocket;

	int defaultPort = 6000;

	@Before
	public void setUp() throws Exception {
		mockedServerSocket = new MockServerSocket(defaultPort);
		testServer = new Server(mockedServerSocket, new RequestParser(), new Responder());
	}
	
	@After
	public void tearDown() throws IOException {
		testServer.shutDown();
	}
		
	@Test
	public void testAcceptsClient() throws IOException {
		testServer.acceptClient();
		assertEquals(mockedClientSocket.getChannel() ,mockedServerSocket.getChannel());
	}

	@Test
	public void testSetsReader() throws IOException {
		testServer.acceptClient();
		assertNull("Reader should be null upon accepting a client", testServer.reader);
		testServer.setReaderAndWriter();
		assertNotNull("Reader was not properly created", testServer.reader);
	}
	
	@Test
	public void testSetsWriter() throws IOException {
		testServer.acceptClient();
		assertNull("Writer should be null upon accepting a client", testServer.writer);
		testServer.setReaderAndWriter();
		assertNotNull("Writer was not properly created", testServer.writer);
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
}
