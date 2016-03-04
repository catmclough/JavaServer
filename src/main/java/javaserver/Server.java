package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private Reader reader;
	private RequestParser parser;
	private Responder responder;
	private SocketWriter writer;

	Server(ServerSocket serverSocket, RequestParser parser, Responder responder) {
		this.serverSocket = serverSocket;
		this.parser = parser;
		this.responder = responder;
	}

	public void run() throws IOException {
		while (true) {
			clientSocket = null;
			acceptClient();
			setReaderAndWriter();
			ClientWorker w = new ClientWorker(clientSocket, reader, parser, responder, writer);
			Thread t = new Thread(w);
			t.start();
	    }
	}

	public void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}
	
	private void setReaderAndWriter() throws IOException {
		this.reader = ServerFactory.createReader(clientSocket);
		this.writer = ServerFactory.createSocketWriter(clientSocket);
	}
}
