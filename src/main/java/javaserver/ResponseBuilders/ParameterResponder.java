package javaserver.ResponseBuilders;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class ParameterResponder implements Responder {

	private String[] supportedMethods;

	public ParameterResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.body(decodedParameterBody(request))
		.build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod(), request.getURIWithoutParams())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(supportedMethods).contains(method);
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
