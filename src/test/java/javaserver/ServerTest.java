package javaserver;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
//import org.mockito.stubbing.Answer;

import junit.framework.TestCase;


public class ServerTest extends TestCase {
	Server server;
	ServerSocket mockedServerSocket;
	Socket mockedClientSocket;
	Reader mockedReader;

	String hostName = "localhost";
	int defaultPort = 5000;
	String simpleGetRequest = "GET / HTTP/1.1\r\n";

	@Before
	public void setUp() {
		this.mockedServerSocket = mock(ServerSocket.class);
		this.mockedReader = mock(Reader.class);
		server = new Server(mockedServerSocket, mockedReader);
	}
	
	@Test
	public void testDefaultPort() {
		assertEquals("Default port was not set.", defaultPort , server.port);
	}

	@Test
	public void testExplicitPortSetting() {
		Server explicitPortServer = new Server(9090, mockedServerSocket, mockedReader);
		assertEquals("Port was not set when constructor was called with port number as argument.", explicitPortServer.port, 9090);
	}

	@Test
	public void testAcceptsOneClient() throws IOException {
		ServerSocket mockedServerSocket = mock(ServerSocket.class);
		server = new Server(mockedServerSocket, mockedReader);

		server.acceptClient();
		verify(mockedServerSocket).accept();
	}
	
	@Test
	public void testReadsSimpleGet() throws IOException {
		mockedClientSocket = mock(Socket.class);
		when(mockedReader.readLine()).thenReturn(simpleGetRequest, "");
		server.readFromSocket();
		verify(mockedReader, atLeastOnce()).readLine();
		verify(mockedReader).close();
	}
	
	@Test
	public void testResponse() throws IOException {
		//mock DataOutputStream
		//mock response String
		//check that writeBytes is called with response and proper end to response

//		String mockOKResponse = "200";
//		DataOutputStream mockedOut = mock(DataOutputStream.class);
//		server.respond(mockedOut, mockOKResponse);
//		verify(mockedOut).close();
	}
}
