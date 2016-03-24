package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.OptionHandler;
import javaserver.HTTPStatusCode;

public class OptionResponseBuilder extends ResponseBuilder {
	OptionHandler requestHandler;
	String requestURI;

	public OptionResponseBuilder(OptionHandler optionRequestHandler) {
		super(optionRequestHandler);
		this.requestHandler = optionRequestHandler;
		this.requestURI = optionRequestHandler.requestURI;
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
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private String getResponseHeader() {
		String header = new String();
		if (requestHandler.requestIsSupported()) {
			header += "Allow: ";
			header += String.join(",", requestHandler.getRouteOptions(requestURI));
		}
		return header;
	}
}
