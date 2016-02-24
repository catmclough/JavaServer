package javaserver;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Before;

import junit.framework.TestCase;


public class ServerTest extends TestCase {
	Server server;
	ServerSocket mockedServerSocket;
	Socket mockedClientSocket;

	String hostName = "localhost";
	int port = Server.DEFAULT_PORT;
	String simpleGetRequest = "GET / HTTP/1.1\r\n";

	@Before
	public void setUp() {
		server = new Server();
	}

	public void testDefaultPort() {
		assertEquals("Default port was not set.", server.port, Server.DEFAULT_PORT);
	}

	public void testExplicitPortSetting() {
		Server explicitPortServer = new Server(9090);
		assertEquals("Port was not set when constructor was called with port number as argument.", explicitPortServer.port, 9090);
	}


	public void testServerSocketCreation() {
		//create instance of server class
		//when listenOnPort() is called on that instance
		//create a mock serverSocket
		//check that server's serverSocket is not null
		//???
	}

	public void testAcceptOneClient() {
		mockedServerSocket = mock(ServerSocket.class);
		try {
			server.acceptClient(mockedServerSocket);
			verify(mockedServerSocket).accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testServerReads() throws IOException {
		mockedClientSocket = mock(Socket.class);
		BufferedReader mockedReader = mock(BufferedReader.class);
		when(mockedReader.readLine()).thenReturn(simpleGetRequest, "");
		server.readFromSocket(mockedReader, mockedClientSocket);
		assertEquals(server.requests.get(server.requests.size() - 1), simpleGetRequest);
	}
	
	public void respond() throws IOException {
		String okResponse = "200";
		DataOutputStream mockedOut = mock(DataOutputStream.class);
		doNothing().when(mockedOut).writeBytes(anyString());
		verify(mockedOut).writeBytes(okResponse);
		server.respond(mockedOut, okResponse);
	}
}
