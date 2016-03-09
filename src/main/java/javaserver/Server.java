package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public ServerFactory serverFactory;
	public Socket clientSocket;
	public ClientWorker clientWorker;
	private ServerSocket serverSocket;
	public Reader reader;
	public RequestBuilder requestBuilder;
	public Responder responder;
	public SocketWriter writer;

	Server(ServerFactory serverFactory, ServerSocket serverSocket, RequestBuilder requestBuilder, Responder responder) {
		this.serverFactory = serverFactory;
		this.serverSocket = serverSocket;
		this.requestBuilder = requestBuilder;
		this.responder = responder;
	}

	public void run() throws IOException {
		clientSocket = null;
		acceptClient();
		setReaderAndWriter();
		clientWorker = serverFactory.createClientWorker(clientSocket, reader, requestBuilder, responder, writer);
		startNewThread(clientWorker);
	}

	public void acceptClient() throws IOException { 
		this.clientSocket = serverSocket.accept();
	}

	private void setReaderAndWriter() throws IOException {
		this.reader = serverFactory.createReader(clientSocket);
		this.writer = serverFactory.createSocketWriter(clientSocket);
	}

	private void startNewThread(ClientWorker clientWorker) {
		Thread t = new Thread(clientWorker);
		t.start();
	}

	public void shutDown() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}
}
