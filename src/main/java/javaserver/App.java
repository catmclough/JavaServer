package javaserver;

import java.io.IOException;

public class App {
	static int PORT = 5000;
	static Server server;
	
	public static void main(String[] args) throws IOException {
		server = ServerFactory.createServer(PORT);
		runServer(server);
	}	
	
	private static void runServer(Server server) throws IOException {
		server.run();
		server.tearDown();
	}
}

