package javaserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javaserver.Server;
import junit.framework.TestCase;

public class ServerTest extends TestCase {
 /* String simpleGetRequest = "GET / HTTP/1.1";*/
	//String okResponse = "HTTP/1.1 200 OK\r\n";
	//String endOfRequest = "\r\n\r\n";
	//String hostName = "localhost";
	 int standardPortNumber = 5000;

	public void testPort() {
		assertEquals(Server.port, standardPortNumber);
	}
/*
	public void testServerSocket() {
		 try (
				 Socket clientSocket = new Socket(hostName, standardPortNumber);
				 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			) {
				out.println(simpleGetRequest + endOfRequest);
				System.out.println(in.readLine());
				assertEquals(in.readLine(), okResponse);
			}
		 catch (UnknownHostException e) {
			 	System.err.println("Don't know about host " + hostName);
			 	System.exit(1);
		 } catch (IOException e) {
			 	System.err.println("Couldn't get I/O for the connection to " + hostName);
			 	System.exit(1);
		 }
	}*/
}
