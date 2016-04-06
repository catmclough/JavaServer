package javaserver;

import java.io.IOException;
import java.net.Socket;
import javaserver.Responders.Responder;

public class ClientWorker implements Runnable {

	private Socket clientSocket;
	protected Reader reader;
	protected SocketWriter writer;

	public ClientWorker(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.reader = new Reader();
		this.writer = new SocketWriter();
	}

	public void run() {
		reader.openReader(clientSocket);
		Request request = RequestParser.createRequest(getRequest(reader));
		Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(request.getURI()));
		Response response = responder.getResponse(request);

		writer.openWriter(clientSocket);
		writer.respond(response.formatResponse());
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Unable to close client socket ");
			e.printStackTrace();
		}
	}

	private String getRequest(Reader reader) {
		String request = "";
		try {
			request = reader.readFromSocket();
		} catch (IOException | NullPointerException e) {
			System.out.println("Did not get any request from socket.");
		}
		return request;
	}
}
