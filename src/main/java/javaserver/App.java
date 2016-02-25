package javaserver;

import java.io.IOException;
import java.net.ServerSocket;

public class App {
	private static Server server;
	private static int PORT = 5000;
	
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		Reader reader = new Reader();
		server = new Server(serverSocket, reader);
		server.run();
	}	
}

