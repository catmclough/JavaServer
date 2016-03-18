package javaserver;

import java.util.Arrays;

public class ResponseBuilder {
	private Request request;
	protected Response response;

	ResponseBuilder(Request request) {
		this.request = request;
	}

	public Response getResponse() {
		this.response = new Response();
		setResponseData(getStatusLine(), getResponseHeader(), getResponseBody());
		return this.response;
	}

	private void setResponseData(String statusLine, String header, String body) {
		response.setStatusLine(statusLine);
		response.setHeader(header);
		response.setBody(body);
	}

	private String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestIsSupported()) {
			if (RequestParser.isRedirect(request)) {
				responseCode = HTTPStatusCode.THREE_OH_TWO;
			} else {
				responseCode = HTTPStatusCode.TWO_HUNDRED;
			}
		} else if (isNotAllowed(request)) {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private boolean requestIsSupported() {
		String requestType = request.getMethod();
		if (getRouteOptions() != null && (Arrays.asList(getRouteOptions()).contains(requestType))) {
			return true;
		} else if (RequestParser.hasVariableParams(request)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isNotAllowed(Request request) {
		String requestType = request.getMethod();
		if (RequestParser.isFileRequest(request) && !(Arrays.asList(getRouteOptions()).contains(requestType))) {
			return true;
		} else {
			return false;
		}
	}

	private String getResponseHeader() {
		String header = new String();
		if (request.getURI().equals("/method_options")) {
			header += "Allow: ";
			header += String.join(",", getRouteOptions());
		} else if (request.getURI().equals("/redirect")) {
		  header += "Location: ";
		  header += request.redirectLocation();
		}
		return header;
	}

	public String[] getRouteOptions() {
		return Routes.getOptions(request.getURI());
	}

	private String getResponseBody() {
		String body = new String();
		if (RequestParser.hasVariableParams(request)) {
			String[] allParams = RequestParser.separateParameters(request.getURI());
			for (int i = 0; i < allParams.length; i++) {
				body += RequestParser.decodeParameters(allParams[i]) + System.lineSeparator();
			}
		}
		return body;
	}
}
