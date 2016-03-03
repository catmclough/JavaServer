package javaserver;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
	private Socket client;
	private Reader reader;
	private SocketWriter writer;
	private Responder responder;
	private String request;

	public ClientWorker(Socket clientSocket, Responder responder) {
		this.client = clientSocket;
		this.responder = responder;
	}

	public void run() {
		try {
			setReader();
			setWriter();
		} catch (IOException e) {
			System.out.println("Setup of reader or writer failed.");
			e.printStackTrace();
		}

		try {
			readAndRespond(this.reader, this.writer);
		} catch(IOException e) {
			System.out.println("Error caught when trying to read or respond in ClientWorker");
		}
	}

	public void setReader() throws IOException {
		this.reader = ServerFactory.createReader(client);
	}

	public void setWriter() throws IOException {
		this.writer = ServerFactory.createSocketWriter(client);
	}

	public void readAndRespond(Reader reader, SocketWriter writer) throws IOException {
		this.request = reader.readFromSocket();
		String[] requestParts = this.request.split(" ");
		String requestType = requestParts[0];
		String destination = requestParts[1];
		responder.respond(requestType, destination, writer);
	}
}

