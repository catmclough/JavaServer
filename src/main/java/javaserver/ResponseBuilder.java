package javaserver;

public class ResponseBuilder {
	private RequestHandler requestHandler;
	private String defaultRedirectLocation = "http://localhost:5000/";

	protected Response response;
	
	ResponseBuilder(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
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
		if (requestHandler.requestIsSupported()) {
			if (requestHandler.isValidRedirectRequest()) {
				responseCode = HTTPStatusCode.THREE_OH_TWO;
			} else {
				responseCode = HTTPStatusCode.TWO_HUNDRED;
			}
		} else if (requestHandler.methodIsNotAllowed()) {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		} else {
		responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private String getResponseHeader() {
		String header = new String();
		if (requestHandler.isValidOptionRequest()) {
			header += "Allow: ";
			header += String.join(",", requestHandler.getRouteOptions());
		} else if (requestHandler.isValidRedirectRequest()) {
		  header += "Location: ";
		  header += defaultRedirectLocation;
		}
		return header;
	}

	private String getResponseBody() {
		String body = new String();
		if (requestHandler.isGetRoot()) {
			body += Routes.getPublicFileNames();
		} else if (requestHandler.routeHasParams()) {
			String[] allParams = requestHandler.separateParameters();
			for (int i = 0; i < allParams.length; i++) {
				body += requestHandler.decodeParameters(allParams[i]) + System.lineSeparator();
			}
		}
		return body;
	}
	
}