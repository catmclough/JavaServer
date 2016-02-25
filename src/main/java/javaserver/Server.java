package javaserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {	
	int port;
	
	private Socket clientSocket;
	private final ServerSocket serverSocket;
	private Reader reader;
	private DataOutputStream output;
	
	private static int DEFAULT_PORT = 5000;
	private static final String OK = "HTTP/1.1 200 OK\r\n";
	private static final String OUTPUT_END_OF_HEADERS = "\r\n\r\n";

	Server(ServerSocket serverSocket, Reader reader) {
		this.port = DEFAULT_PORT;
		this.serverSocket = serverSocket;
		this.reader = reader;
	}

	Server(int port, ServerSocket serverSocket, Reader reader) {
		this.port = port;
		this.serverSocket = serverSocket;
		this.reader = reader;
	}	
	
	public void run() throws IOException {
		this.clientSocket = acceptClient();
		readFromSocket();
		this.output = createOutput(clientSocket);
		respond(OK);
		closeSocket(clientSocket);
	}

	public Socket acceptClient() {
		try {
			Socket clientSocket = this.serverSocket.accept();
			return clientSocket;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void readFromSocket() throws IOException {
		reader.initialize(clientSocket);
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;
		}
		reader.close();
	}
	
	private DataOutputStream createOutput(Socket clientSocket) throws IOException {
		return new DataOutputStream(clientSocket.getOutputStream());
	}

	public void respond(String response) throws IOException {
		output.writeBytes(response + OUTPUT_END_OF_HEADERS); 
		output.close();
	}

	public void closeSocket(Socket socket) throws IOException {
		socket.close();
	}
}
