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
		Server server = new Server(this, new ServerSocket(port), new Responder());
		return server;
	}

	public Reader createReader(Socket clientSocket) throws IOException {
		InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
		BufferedReader readingMechanism = new BufferedReader(input);
		return new Reader(readingMechanism);
	}

	public SocketWriter createSocketWriter(Socket clientSocket) throws IOException {
		OutputStream outputStream = clientSocket.getOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		return new SocketWriter(output);
	}

	public ClientWorker createClientWorker(Reader reader, Responder responder, SocketWriter writer) {
		return new ClientWorker(reader, responder, writer);
	}

	public static ResponseBuilder createResponseBuilder(Request request) {
		return new ResponseBuilder(request);
	}
}

