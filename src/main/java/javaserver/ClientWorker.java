package javaserver;

import java.io.IOException;

public class ClientWorker implements Runnable {
	ServerFactory serverFactory;
	private Reader reader;
	public SocketWriter writer;
	private Responder responder;

	public ClientWorker(ServerFactory serverFactory, Reader reader, Responder responder, SocketWriter writer) {
		this.serverFactory = serverFactory;
		this.reader = reader;
		this.responder = responder;
		this.writer = writer;
	}

	public void run() {
		String rawRequest = getRequest();
		Request request = new Request(RequestParser.getRequestMethod(rawRequest), RequestParser.getRequestURI(rawRequest));
		respond(request);
		writer.closeOutputStream();
	}

	private String getRequest() {
		String request = "";
		try {
			request = reader.readFromSocket();
		} catch (IOException e) {
			System.out.println("Unable to read request");
		}
		return request;
	}

	private void respond(Request request) {
		try {
			responder.respond(request, serverFactory, writer);
		} catch (IOException e) {
			System.out.println("ClientWorker was unable to respond to client socket");
		}
	}
}

