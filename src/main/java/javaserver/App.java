package javaserver;

import java.io.IOException;

public class App {
	protected static final int PORT = 5000;
	protected static Server server;

	protected static boolean isOn = false;

	public static void main(String[] args) throws IOException {
		setUpServer();
		configureRoutes();
		runServer(server);
	}

	protected static void setUpServer() throws IOException {
		ServerFactory serverFactory = new ServerFactory();
		server = serverFactory.createServer(PORT);
	}

	protected static void configureRoutes() {
		Routes.configure();
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
