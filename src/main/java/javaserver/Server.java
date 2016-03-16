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

	Server(ServerFactory serverFactory, ServerSocket serverSocket) {
		this.serverFactory = serverFactory;
		this.serverSocket = serverSocket;
	}

	public void run() throws IOException {
		acceptClient();
		setReaderAndWriter();
		openNewThread();
	}

	public void shutDown() throws IOException {
		serverSocket.close();
	}

	private void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}

	private void setReaderAndWriter() throws IOException {
		this.reader = serverFactory.createReader(clientSocket);
		this.writer = serverFactory.createSocketWriter(clientSocket);
	}

	private void openNewThread() {
		clientWorker = serverFactory.createClientWorker(reader, writer);
		Thread t = createNewThread(clientWorker);
		startThread(t);
	}

	protected Thread createNewThread(ClientWorker clientWorker) {
		return new Thread(clientWorker);
	}

	protected void startThread(Thread thread) {
		thread.run();
	}
}
