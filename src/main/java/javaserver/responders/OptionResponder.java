package javaserver.responders;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;

public class OptionResponder implements Responder {

	private String[] supportedMethods;

	public OptionResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new Response.ResponseBuilder(getStatusLine(request))
          .header(getResponseHeader(request))
          .build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(supportedMethods, request.getMethod())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
		}
	}

	private String getResponseHeader(Request request) {
		String header = new String();
		if (requestIsSupported(supportedMethods, request.getMethod())) {
			header += "Allow: ";
			header += String.join(",", supportedMethods);
		}
		return header;
	}
}