package javaserver;

import java.io.IOException;

public class App {
	static int PORT = 5000;
	static Server server;
	private static boolean isOn = false;

	public static void main(String[] args) throws IOException {
		ServerFactory serverFactory = new ServerFactory();
		setUpServer(serverFactory);
		configureRoutes();
		runServer(server);
	}

	public static void setUpServer(ServerFactory serverFactory) throws IOException {
		server = serverFactory.createServer(PORT);
	}

	public static void configureRoutes() {
		Routes.setUp();
	}

	public static void runServer(Server server) throws IOException {
		isOn = true;
		while (isOn) {
			server.run();
		}
	}

	public static void close() {
		isOn = false;
	}
}

