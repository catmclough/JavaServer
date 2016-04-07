package javaserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class App {

	public static String name = "Cat's Java Server";
	protected static final int DEFAULT_PORT = 5000;
	protected static int port;
	protected static final String DEFAULT_PUBLIC_DIRECTORY = "public/";
	protected static PublicDirectory publicDirectory = initializeDirectoryRouter();
	protected static Server server;

	public static void main(String[] args) throws IOException {
		setUpServer(args);
		runServer(server);
	}

	public static PublicDirectory initializeDirectoryRouter() {
		return new PublicDirectory(DEFAULT_PUBLIC_DIRECTORY);
	}

	protected static void setUpServer(String[] args) throws IOException {
		port = ArgHandler.getPort(args, DEFAULT_PORT);
		try {
			setDirectory(args);
		} catch (DirectoryNotFoundException e) {
			e.getMessage();
			System.exit(0);
		}
		server = new Server(new ServerSocket(port));
	}

	protected static void setDirectory(String[] args) throws DirectoryNotFoundException {
		String specifiedDirectoryName = ArgHandler.getChosenDirectoryName(args);
		if (specifiedDirectoryName != null) {
			publicDirectory = new PublicDirectory(specifiedDirectoryName);
			if (!directoryExists(publicDirectory.getDirectoryName())) {
				System.out.println(publicDirectory.getDirectoryName() + " does not exist.");
				throw new DirectoryNotFoundException(specifiedDirectoryName);
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

	public static PublicDirectory getPublicDirectory() {
		return publicDirectory;
	}
}
