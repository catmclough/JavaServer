package javaserver.ResponseBuilders;

import java.util.Arrays;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class RedirectResponder implements Responder {

	private String[] supportedMethods;
	private String defaultRedirectLocation = "http://localhost:5000/";

	public RedirectResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		  return new ResponseBuilder()
		    .statusLine(getStatusLine(request))
		    .header(getResponseHeader(request))
		    .build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			return HTTPStatusCode.THREE_OH_TWO.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
		}
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(supportedMethods).contains(method);
	}

	private String getResponseHeader(Request request) {
		String header = new String();
		if (requestIsSupported(request.getMethod(), request.getURI())) {
		  header += "Location: ";
		  header += defaultRedirectLocation;
		}
		return header;
	}
}
