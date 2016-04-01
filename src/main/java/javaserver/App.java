package javaserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class App {

	public static String name = "Cat's Java Server";
	protected static final int DEFAULT_PORT = 5000;
	protected static int port;
	protected static final String DEFAULT_PUBLIC_DIRECTORY = "public";
	protected static PublicDirectory publicDirectory = new PublicDirectory(DEFAULT_PUBLIC_DIRECTORY);
	protected static Server server;

	public static void main(String[] args) throws IOException {
		setUpServer(args);
		runServer(server);
	}

	protected static void setUpServer(String[] args) throws IOException {
		port = ArgHandler.getPort(args, DEFAULT_PORT);
		setDirectory(args);
		server = new Server(new ServerSocket(port));
	}

	private static void setDirectory(String[] args) {
		String specifiedDirectoryName = ArgHandler.getChosenDirectoryName(args);
		if (specifiedDirectoryName != null) {
			publicDirectory = new PublicDirectory(specifiedDirectoryName);
			if (!directoryExists(publicDirectory.getDirectoryName())) {
				System.out.println(publicDirectory.getDirectoryName() + " does not exist.");
				throw new Error();
			}
		}
	}

	protected static void runServer(Server server) throws IOException {
		System.out.println("Server running on port " + port + "\nPublic Directory: " + publicDirectory.getRoute());
		server.run();
	}

	private static boolean directoryExists(String directoryName) {
		File directory = new File(directoryName);
		return directory.exists();
	}
}
