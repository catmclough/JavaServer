package javaserver.ResponseBuilders;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class ErrorResponder implements Responder {

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.build();
	}

	@Override
	public String getStatusLine(Request request) {
		return HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	}
}
