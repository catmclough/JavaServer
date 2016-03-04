package javaserver;

import java.io.IOException;
import java.net.Socket;

public class ClientWorker implements Runnable {
	private Reader reader;
	public SocketWriter writer;
	private Responder responder;
	private RequestParser parser;
	
	public ClientWorker(Socket clientSocket, Reader reader, RequestParser parser, Responder responder, SocketWriter writer) {
		this.reader = reader;
		this.parser = parser;
		this.responder = responder;
		this.writer = writer;
	}
	
	public void run() {
		String request = getRequest();
		respond(request);
		
		try {
			writer.closeOutputStream();
		} catch (IOException e) {
			System.out.println("Unable to close Writer's OutputStream");;
		}
	}
	
	private String getRequest() {
		return reader.readFromSocket();
	}
	
	private void respond(String request) {
		responder.respond(parser.getRequestType(request), parser.getRequestURI(request), writer);
	}
}

