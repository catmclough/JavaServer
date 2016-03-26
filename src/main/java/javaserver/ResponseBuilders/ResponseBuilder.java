package javaserver.ResponseBuilders;

import java.util.Arrays;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.Routes;

public class ResponseBuilder {
	protected Response response;
	protected Request request;
	
	public ResponseBuilder(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		this.response = new Response();
		setResponseData();
		return this.response;
	}

	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
	}

	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}
	
	protected boolean requestIsSupported(String method, String requestURI) {
		return routeExists(requestURI) && Arrays.asList(Routes.routeOptions.get(requestURI)).contains(method);
	}
	
	private boolean routeExists(String requestURI) {
		return Routes.routeOptions.get(requestURI) != null;
	}
}