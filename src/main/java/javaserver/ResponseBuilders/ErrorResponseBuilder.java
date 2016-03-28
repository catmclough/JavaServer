package javaserver.ResponseBuilders;


import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;

public class ErrorResponseBuilder implements ResponseBuilder {
	private Response response;

	@Override
	public Response getResponse(Request request) {
		this.response = new Response();
		setResponseData(request);
		return this.response;
	}

	@Override
	public void setResponseData(Request request) {
		response.setStatusLine(getStatusLine(request));
	}

	@Override
	public String getStatusLine(Request request) {
		HTTPStatusCode responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		return responseCode.getStatusLine();
	}
}
