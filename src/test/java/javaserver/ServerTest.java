package javaserver;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.TestCase;

public class ServerTest extends TestCase {
	private Server testServer;
	private ServerSocket mockedServerSocket;
	Socket mockedClientSocket;
	private Reader mockedReader;
	private SocketWriter mockedWriter;

	int defaultPort = 5000;
	String simpleGetRequest = "GET / HTTP/1.1\r\n";
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public void setUp() {
		this.mockedServerSocket = mock(ServerSocket.class);
		this.mockedReader = mock(Reader.class);
		this.mockedWriter = mock(SocketWriter.class);
		this.testServer = spy(new Server(mockedServerSocket));
		this.mockedClientSocket = mock(Socket.class);
	}

	
//	@Test
//	public void testRuns() throws IOException {
		//test that it accepts a client
//		when(this.mockedServerSocket.accept()).thenReturn(this.mockedClientSocket);
//		doNothing().when(testServer).setReaderAndWriter();
//		verify(this.mockedServerSocket).accept();
//		assertEquals(testServer.clientSocket, mockedClientSocket);

		//test that it sets a reader and writer
		//test that it tells the reader to read from socket
		//test that it tells the writer to respond
//	}
	
	@Test
	public void testAcceptsClient() throws IOException {
	}
//	
//	@Test
//	public void testServerRuns() throws IOException {
//		this.testServer.run();
//		verify(mockedReader, atLeastOnce()).readFromSocket(any(Socket.class));
//		verify(mockedWriter, atLeastOnce()).respond(anyString());
//	}
	
//	@Test 
//	public void testTearDown() throws IOException {
////		testServer.tearDown(mockedReader, mockedWriter, mockedClientSocket);
//		verify(mockedReader).close();
//		verify(mockedWriter).close();
//		verify(mockedClientSocket).close();
//	}
}
