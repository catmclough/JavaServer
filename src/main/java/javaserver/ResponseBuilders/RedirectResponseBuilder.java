package javaserver.ResponseBuilders;

import javaserver.HTTPStatusCode;
import javaserver.Request;

public class RedirectResponseBuilder extends ResponseBuilder {
	private String defaultRedirectLocation = "http://localhost:5000/";

	public RedirectResponseBuilder(Request request) {
		super(request);
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
		response.setHeader(getResponseHeader());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.THREE_OH_TWO;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private String getResponseHeader() {
		String header = new String();
		if (requestIsSupported(request.getMethod(), request.getURI())) {
		  header += "Location: ";
		  header += defaultRedirectLocation;
		}
		return header;
	}

}
