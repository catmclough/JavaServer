package javaserver;

import java.io.IOException;
import java.util.HashMap;

public class ClientWorker implements Runnable {
	private Reader reader;
	public SocketWriter writer;
	private Responder responder;
	private RequestBuilder requestBuilder;
	
	public ClientWorker(Reader reader, RequestBuilder requestBuilder, Responder responder, SocketWriter writer) {
		this.reader = reader;
		this.requestBuilder = requestBuilder;
		this.responder = responder;
		this.writer = writer;
	}
	
	public void run() {
		String rawRequest = getRequest();
		HashMap<String, String> request = requestBuilder.buildRequest(rawRequest);
		respond(request);
		try {
			writer.closeOutputStream();
		} catch (IOException e) {
			System.out.println("Unable to close Writer's OutputStream");;
		}
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
	
	private void respond(HashMap<String, String> request) {
		responder.respond(request, writer);
	}
}

