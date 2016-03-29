package javaserver.ResponseBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.Routes;

public class ParameterResponseBuilder implements ResponseBuilder {

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
		response.setBody(decodedParameterBody(request));
	}

	@Override
	public String getStatusLine(Request request) {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURIWithoutParams())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(Routes.routeOptions.get(requestURI)).contains(method);
	}

	private String decodedParameterBody(Request request) {
		String body = "";
		for (String parameterVar : splitParameters(request.getURI())) {
			body += decodeParameter(parameterVar) + System.lineSeparator();
		}
		return body;
	}

	public String[] splitParameters(String uri) {
		String query = getParams(uri);
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

	private String getParams(String uri) {
		String[] routeParts = uri.split("\\?", 2);
		return routeParts[1];
	}

}
