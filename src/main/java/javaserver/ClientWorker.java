package javaserver;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
	private Socket client;
	private Reader reader;
	private SocketWriter writer;

	private static final String OK = "HTTP/1.1 200 OK\r\n";
	private static final String END_OF_HEADERS = "\r\n\r\n";

	public ClientWorker(Socket clientSocket) {
		this.client = clientSocket;
	}

	//called by Thread#start
	public void run() {
    boolean serverIsReading = true;
    try {
      setReaderAndWriter();
  	} catch (IOException e) {
      System.out.println("Setup of reader or writer failed.");
      e.printStackTrace();
		}

    while(serverIsReading) {
      try {
        reader.readFromSocket();
        writer.respond(OK + END_OF_HEADERS);
      } catch (IOException e) {
        //this error is caught every time this loop does not get anything from the reader
      }
	  }
	}

	public void setReaderAndWriter() throws IOException {
		this.reader = ServerFactory.createReader(client);
		this.writer = ServerFactory.createSocketWriter(client);
	}
}
