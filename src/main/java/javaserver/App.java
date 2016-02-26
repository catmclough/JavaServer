package javaserver;

import java.io.IOException;

public class App {
	static int PORT = 5000;
	static Server server;
	
	public static void main(String[] args) throws IOException {
		ServerFactory serverFactory = new ServerFactory();
		setUpServer(serverFactory);
		runServer(server);
	}	
	
	public static void setUpServer(ServerFactory serverFactory) throws IOException {
		server = serverFactory.createServer(PORT);
	}
	
	public static void runServer(Server server) throws IOException {
		server.run();
		server.tearDown();
	}
}

