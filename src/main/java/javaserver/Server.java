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
	public Responder responder;
	public SocketWriter writer;

	Server(ServerFactory serverFactory, ServerSocket serverSocket, Responder responder) {
		this.serverFactory = serverFactory;
		this.serverSocket = serverSocket;
		this.responder = responder;
	}

	public void run() throws IOException {
		acceptClient();
		setReaderAndWriter();
		openNewThread();
	}

	private void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}

	private void setReaderAndWriter() throws IOException {
		this.reader = serverFactory.createReader(clientSocket);
		this.writer = serverFactory.createSocketWriter(clientSocket);
	}

	private void openNewThread() {
		clientWorker = serverFactory.createClientWorker(reader, responder, writer);
		Thread t = createNewThread(clientWorker);
		startThread(t);
	}

	private Thread createNewThread(ClientWorker clientWorker) {
		return new Thread(clientWorker);
	}

	private void startThread(Thread thread) {
		thread.run();
	}

	public void shutDown() throws IOException {
		clientSocket.close();
		serverSocket.close();
	}
}
