package javaserver;

import java.io.IOException;

public class ClientWorker implements Runnable {
	private Reader reader;
	public SocketWriter writer;
	private ResponseBuilder responder;

	ClientWorker(Reader reader, SocketWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	public void run() {
		String rawRequest = getRequest();
		Request request = RequestParser.createRequest(rawRequest);
		ResponseBuilder responder = Routes.routeResponders.get(request.getURI());
		if (responder == null)
			responder = new ErrorResponseBuilder();
		writer.respond(responder.getResponse(request).formatResponse());
	}

	private String getRequest() {
		String request = "";
		try {
			request = reader.readFromSocket();
		} catch (IOException e) {
			System.out.println("Unable to read from socket.");
		}
		return request;
	}
}
