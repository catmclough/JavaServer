package javaserver.ResponseBuilders;

import java.io.UnsupportedEncodingException;

import javaserver.HTTPStatusCode;
import javaserver.Request;

public class ParameterResponseBuilder extends ResponseBuilder {

	public ParameterResponseBuilder(Request request) {
		super(request);
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
		response.setBody(decodedParameterBody());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURIWithoutParams())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}

	private String decodedParameterBody() {
		String body = "";
		for (String parameterVar : splitParameters()) {
			body += decodeParameter(parameterVar) + System.lineSeparator();
		}
		return body;
	}
	
	
	public String[] splitParameters() {
		String query = separateParams();
		query = query.replace("=", " = ");
		return query.split("&");
	}

	public String decodeParameter(String parameterLine) {
		try {
			String encoding = "UTF-8";
			parameterLine = java.net.URLDecoder.decode(parameterLine, encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ParameterHandler could not decode one or more of the request's parameters");
		}
		return parameterLine;
	}

	private String separateRoute() {
		String[] routeParts = request.getURI().split("\\?", 2);
		return routeParts[0];
	}

	private String separateParams() {
		String[] routeParts = request.getURI().split("\\?", 2);
		return routeParts[1];
	}
}
