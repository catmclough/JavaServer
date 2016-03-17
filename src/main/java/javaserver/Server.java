package javaserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public Socket clientSocket;
	public ClientWorker clientWorker;
	private ServerSocket serverSocket;
	private ThreadManager threadManager;
	public Reader reader;
	public ResponseBuilder responder;
	public SocketWriter writer;

	Server(ServerSocket serverSocket, ThreadManager threadManager) {
		this.serverSocket = serverSocket;
		this.threadManager = threadManager;
	}

	public void run() throws IOException {
		acceptClient();
		setReaderAndWriter();
		threadManager.openNewThread(this.reader, this.writer);
	}

	public void shutDown() throws IOException {
		serverSocket.close();
	}

	private void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}

	private void setReaderAndWriter() throws IOException {
		InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
		BufferedReader readingMechanism = new BufferedReader(input);
		this.reader = new Reader(readingMechanism);
		OutputStream outputStream = clientSocket.getOutputStream();
		DataOutputStream output = new DataOutputStream(outputStream);
		this.writer = new SocketWriter(output);
	}

}
