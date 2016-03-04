package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public Socket clientSocket;
	private ServerSocket serverSocket;
	public Reader reader;
	public RequestParser parser;
	public Responder responder;
	public SocketWriter writer;

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
			startNewThread(w);
	    }
	}

	private void startNewThread(ClientWorker worker) {
		Thread t = new Thread(worker);
		t.start();
	}

	public void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}

	public void setReaderAndWriter() throws IOException {
		this.reader = ServerFactory.createReader(clientSocket);
		this.writer = ServerFactory.createSocketWriter(clientSocket);
	}

	public void shutDown() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}
}
