package javaserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFactory {
	public Server createServer(int port) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		Server server = new Server(serverSocket);;
		return server;
	}

	public static Reader createReader(Socket clientSocket) throws IOException {
		InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
		BufferedReader readingMechanism = new BufferedReader(input);
		return new Reader(readingMechanism);
	}

	public static SocketWriter createSocketWriter(Socket clientSocket) throws IOException {
		OutputStream outputStream = clientSocket.getOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		return new SocketWriter(output);
	}
}
