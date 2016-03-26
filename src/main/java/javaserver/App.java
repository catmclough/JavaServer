package javaserver;

import java.io.IOException;
import java.net.ServerSocket;

public class App {
	protected static final int PORT = 5000;
	protected static Server server;

	protected static boolean isOn = false;

	public static void main(String[] args) throws IOException {
		setUpServer();
		runServer(server);
	}

	protected static void setUpServer() throws IOException {
		server = new Server(new ServerSocket(PORT), new ThreadManager());
	}

	protected static void runServer(Server server) throws IOException {
		isOn = true;
		while (isOn) {
			server.run();
		}
	}

	protected static void close() {
		isOn = false;
	}
}
