package javaserver;

import java.io.UnsupportedEncodingException;

public class ParameterHandler {
	Request request;

	ParameterHandler(Request request) {
		this.request = request;
	}

	public String separateRoute(String URI) {
		String[] routeParts = URI.split("\\?", 2);
		return routeParts[0];
	}

	public String separateParams(String URI) {
		String[] routeParts = URI.split("\\?", 2);
		return routeParts[1];
	}

	public String decodeParameters(String parameterLine) {
		try {
			String encoding = "UTF-8";
			parameterLine = java.net.URLDecoder.decode(parameterLine, encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ParameterHandler could not decode one or more of the request's parameters");
		}
		return parameterLine;
	}

	public String[] splitParameters(String URI) {
		String query = separateParams(URI);
		query = query.replace("=", " = ");
		return query.split("&");
	}
}
