package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	Socket clientSocket;
	private ServerSocket serverSocket;
	private boolean isRunning;

	Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.isRunning = true;
	}

	public void run() throws IOException {
		ClientWorker w;
		while (isOn()) {
			clientSocket = null;
			acceptClient();
			w = new ClientWorker(this.clientSocket);
			Thread t = new Thread(w);
			t.start();
	  }
		clientSocket.close();
	}

	private boolean isOn() {
		return this.isRunning;
	}

	void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}
}

