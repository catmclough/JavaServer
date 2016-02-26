package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {	
	public Socket clientSocket;
	private ServerSocket serverSocket;
	private Reader reader;
	private SocketWriter writer;
	
	private static final String OK = "HTTP/1.1 200 OK\r\n";
	private static final String END_OF_HEADERS = "\r\n\r\n";

	Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void run() throws IOException {
		acceptClient();
		setReaderAndWriter();
		reader.readFromSocket();
		writer.respond(OK + END_OF_HEADERS);
	}

	private void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}
	
	public void setReaderAndWriter() throws IOException {
		this.reader = ServerFactory.createReader(clientSocket);
		this.writer = ServerFactory.createSocketWriter(clientSocket);
	}
	
	public void tearDown() throws IOException {
		reader.close();
		writer.close();
		clientSocket.close();
	}
}
