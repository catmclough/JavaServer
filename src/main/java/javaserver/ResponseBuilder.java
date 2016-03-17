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
		if (isSupported(request)) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}
	
	private boolean isSupported(Request request) {
		String requestType = request.getMethod();
		if (request.routeOptions() != null && (Arrays.asList(request.routeOptions()).contains(requestType))) {
			return true;
		} else if (request.hasVariableParams()) {
			return true;
		} else {
			return false;
		}
	}

	private String getResponseHeader() {
		String header = new String();
		if (request.getURI().equals("/method_options")) {
			header += "Allow: ";
			header += String.join(",", request.routeOptions());
		}
		return header;
	}

	private String getResponseBody() {
		String body = new String();
		if (request.hasVariableParams()) {
			String[] allParams = RequestParser.separateParameters(request.getURI());
			for (int i = 0; i < allParams.length; i++) {
				body += RequestParser.decodeParameters(allParams[i]) + System.lineSeparator();
			}
		}
		return body;
	}
}
