package javaserver;

import java.io.IOException;

public class Responder {
	Request request;

	public void respond(Request request, SocketWriter writer) throws IOException {
		this.request = request;
		Response response = getResponse();
		String formattedResponse = response.getFormattedResponse();
		writer.respond(formattedResponse);
	}

	private Response getResponse() {
		ResponseBuilder responseBuilder = ServerFactory.createResponseBuilder(request);
		return responseBuilder.createResponse();
	}
}
