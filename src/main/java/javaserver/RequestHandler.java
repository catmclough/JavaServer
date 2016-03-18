package javaserver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RequestHandler {
	private Request request;
	
	RequestHandler(Request request) {
		this.request = request;
	}
	
	public boolean requestIsSupported() {
		return getRouteOptions() != null && ((Arrays.asList(getRouteOptions()).contains(request.getMethod())) || hasValidParameters());
	}

	public String[] getRouteOptions() {
		return Routes.getOptions(request.getURI());
	}

	public boolean isValidRedirectRequest() {
		String path = request.getURI();
		return path.equals("/redirect");
	}
	
	public boolean isValidOptionRequest() {
		String path = request.getURI();
		return path.equals("/method_options");
	}

	public boolean isNotAllowed() {
		String requestType = request.getMethod();
		if (isFileRequest() && !(Arrays.asList(getRouteOptions()).contains(requestType))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasParameters() {
		return request.getURI().contains("?");
	}
	
	private boolean hasValidParameters() {
		//TODO: how do I tell if parameters are valid?
		return hasParameters();
	}

	public String decodeParameters(String parameterLine) {
		try {
			String encoding = "UTF-8";
			parameterLine = java.net.URLDecoder.decode(parameterLine, encoding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ResponseBuilder could not decode one or more of the request's parameters");
		}
		return parameterLine;
	}

	public boolean isFileRequest() {
		return Arrays.asList(Routes.FILES).contains(request.getURI());
	}

	public String[] separateParameters() {
		String params = request.getURI().split("/parameters?.")[1];
		params = params.replace("=", " = ");
		return params.split("&");
	}
}
