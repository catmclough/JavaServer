package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.RedirectHandler;
import javaserver.HTTPStatusCode;

public class RedirectResponseBuilder extends ResponseBuilder {
	RedirectHandler requestHandler;
	private String defaultRedirectLocation = "http://localhost:5000/";
	
	public RedirectResponseBuilder(RedirectHandler redirectHandler) {
		super(redirectHandler);
		this.requestHandler = redirectHandler;
	}
	
	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
		response.setHeader(getResponseHeader());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestHandler.requestIsSupported()) {
			responseCode = HTTPStatusCode.THREE_OH_TWO;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private String getResponseHeader() {
		String header = new String();
		if (requestHandler.requestIsSupported()) {
		  header += "Location: ";
		  header += defaultRedirectLocation;
		}
		return header;
	}

}
