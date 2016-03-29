package javaserver.ResponseBuilders;

import java.util.Arrays;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.Routes;

public class RedirectResponseBuilder implements ResponseBuilder {

	private Response response;
	private String defaultRedirectLocation = "http://localhost:5000/";

	@Override
	public Response getResponse(Request request) {
		this.response = new Response();
		setResponseData(request);
		return this.response;

	@Override
	public void setResponseData(Request request) {
		response.setStatusLine(getStatusLine(request));
		response.setHeader(getResponseHeader(request));
	}

	@Override
	public String getStatusLine(Request request) {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.THREE_OH_TWO;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(Routes.routeOptions.get(requestURI)).contains(method);
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
