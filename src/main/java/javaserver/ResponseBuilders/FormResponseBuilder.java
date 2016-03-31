package javaserver.ResponseBuilders;

import java.util.Arrays;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.Routes;

public class FormResponseBuilder implements ResponseBuilder {

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
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(Routes.routeOptions.get(requestURI)).contains(method);
	}
}
