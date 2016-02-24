package javaserver;

import java.io.IOException;

public class App {
	private static final String OK = "HTTP/1.1 200 OK\r\n";
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.listenOnPort();
		server.acceptClient(server.serverSocket);
		server.readFromSocket(server.createReaderWithInput(server.clientSocket), server.clientSocket);
		server.respond(server.createOutputStream(), OK);
		server.tearDown();
	}
}

