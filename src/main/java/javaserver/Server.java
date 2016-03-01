package javaserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	Socket clientSocket;
	private ServerSocket serverSocket;

	Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void run() throws IOException {
		try {
			ClientWorker w;
			while (true) {
				  w = new ClientWorker(serverSocket.accept());
          Thread t = new Thread(w);
          t.start();
      }
    } catch(IOException e) {
        e.printStackTrace();
        System.out.println("Exception Caught in Server#run!");
    }
	}

	void acceptClient() throws IOException {
		this.clientSocket = serverSocket.accept();
	}
}

