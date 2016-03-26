package javaserver.ResponseBuilders;

import javaserver.HTTPStatusCode;
import javaserver.Request;

public class FormResponseBuilder extends ResponseBuilder {

	public FormResponseBuilder(Request request) {
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
			responseCode = HTTPStatusCode.FOUR_OH_FOUR;
		}
		return responseCode.getStatusLine();
	}
}
