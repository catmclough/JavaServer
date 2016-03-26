package javaserver.ResponseBuilders;

import javaserver.HTTPStatusCode;
import javaserver.Request;

public class FileResponseBuilder extends ResponseBuilder {

	public FileResponseBuilder(Request request) {
		super(request);
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}
}
