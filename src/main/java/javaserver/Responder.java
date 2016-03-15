package javaserver;

import java.io.IOException;
import java.util.HashMap;

public class Responder {
	RequestBuilder requestBuilder;
	ResponseBuilder responseBuilder;

	Responder(ResponseBuilder responseBuilder) {
		this.responseBuilder = responseBuilder;
	}

	public String respond(HashMap<String, String> request, SocketWriter writer) throws IOException {
		String response = responseBuilder.getResponse(request);
		writer.respond(response);
		return response;
	}
}
