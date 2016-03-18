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
		Request request = new Request(RequestParser.getRequestMethod(rawRequest), RequestParser.getRequestURI(rawRequest));
		RequestHandler requestHandler = new RequestHandler(request);
		this.responder = new ResponseBuilder(requestHandler);
		respond(request);
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
			Response response = responder.getResponse();
			writer.respond(response.formatResponse());
		} catch (IOException e) {
			System.out.println("ClientWorker unable to get response from Responder or write Response with SocketWriter");
		}
	}
}
