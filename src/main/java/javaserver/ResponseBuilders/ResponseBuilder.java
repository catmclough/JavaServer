package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.RequestHandler;
import javaserver.HTTPStatusCode;
import javaserver.Response;

public class ResponseBuilder {
	private RequestHandler requestHandler;
	protected Response response;

	public ResponseBuilder(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
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
		if (requestHandler.requestIsSupported()) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}
}
