package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.ParameterHandler;
import javaserver.HTTPStatusCode;

public class ParameterResponseBuilder extends ResponseBuilder {
	ParameterHandler requestHandler;

	public ParameterResponseBuilder(ParameterHandler paramHandler) {
		super(paramHandler);
		this.requestHandler = paramHandler;
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
		response.setBody(decodedParameterBody());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestHandler.requestIsSupported()) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}

	private String decodedParameterBody() {
		String body = "";
		for (String parameterVar : requestHandler.splitParameters()) {
			body += requestHandler.decodeParameter(parameterVar) + System.lineSeparator();
		}
		return body;
	}
}
