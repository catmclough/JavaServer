package javaserver;

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
		this.testServer = spy(new Server(mockedServerSocket));
		this.mockedClientSocket = mock(Socket.class);
		testServer.clientSocket = mockedClientSocket;
		this.mockedReader = mock(Reader.class);
		this.mockedWriter = mock(SocketWriter.class);
		testServer.reader = mockedReader;
		testServer.writer = mockedWriter;
	}
	
	@Test
	public void testRuns() throws IOException {
		when(this.mockedServerSocket.accept()).thenReturn(this.mockedClientSocket);
		doNothing().when(testServer).setReaderAndWriter();

		testServer.run();
		verify(this.mockedServerSocket).accept();
		assertEquals(mockedClientSocket, testServer.clientSocket);

		verify(testServer).setReaderAndWriter();
		assertTrue(testServer.reader != null);
		assertTrue(testServer.writer != null);
		
		verify(testServer.reader).readFromSocket();

		verify(testServer.writer).respond(anyString());
	}
	
	
	@Test 
	public void testTearDown() throws IOException {
		testServer.tearDown();
		verify(testServer.reader).close();
		verify(testServer.writer).close();
		verify(testServer.clientSocket).close();
	}
}
