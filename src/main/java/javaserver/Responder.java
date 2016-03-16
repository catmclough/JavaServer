package javaserver;

import java.io.IOException;

public class Responder {
	Request request;
	ServerFactory serverFactory;

	public void respond(Request request, ServerFactory serverFactory, SocketWriter writer) throws IOException {
		this.serverFactory = serverFactory;
		this.request = request;
		Response response = getResponse();
		String formattedResponse = response.getFormattedResponse();
		writer.respond(formattedResponse);
	}

	private Response getResponse() {
		ResponseBuilder responseBuilder = serverFactory.createResponseBuilder(request);
		return responseBuilder.createResponse();
	}
}
