package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		testServer = new Server(mockedServerSocket);
	}

	@Test
	public void testAcceptsClient() throws IOException {
		testServer.acceptClient();
		assertEquals(mockedClientSocket.getChannel() ,mockedServerSocket.getChannel());
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
