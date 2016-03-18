package javaserver;

import java.io.UnsupportedEncodingException;

public class RequestParser {

	public static String getRequestMethod(String rawRequest) {
		return splitRequest(rawRequest)[0];
	}

	public static String getRequestURI(String rawRequest) {
		return splitRequest(rawRequest)[1];
	}

	private static String[] splitRequest(String rawRequest) {
		return rawRequest.split(" ");
	}

	public static String[] separateParameters(String URI) {
		String params = URI.split("/parameters?.")[1];
		params = params.replace("=", " = ");
		return params.split("&");
	}
	
	public static String decodeParameters(String parameterLine) {
		try {
			String encoding = "UTF-8";
			parameterLine = java.net.URLDecoder.decode(parameterLine, encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ResponseBuilder could not decode one or more of the request's parameters");
		}
		return parameterLine;
	}
	
	
	public static boolean hasVariableParams(Request request) {
		return request.getURI().contains("?");
	}
	
	public static boolean isRedirect(Request request) {
		return request.getURI().contains("redirect");
	}

}
