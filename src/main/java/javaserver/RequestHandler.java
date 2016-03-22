package javaserver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RequestHandler {
	private Request request;
	private String requestMethod;
	private String requestURI;
	private String parameters;
	
	RequestHandler(Request request) {
		this.request = request;
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
	}
	
	public boolean requestIsSupported() {
		if (isAcceptableRequest(requestMethod, requestURI)) {
			return true;
		} else if (routeHasParams()) {
			separateRouteFromParams(requestURI);
			if (isAcceptableRequest(requestMethod, requestURI)) { 
				System.out.println("here");
				return true;
			}
		}
		return false;
	}

	private boolean routeExists() {
		return getRouteOptions() != null;
	}
	
	public String[] getRouteOptions() {
		return Routes.getOptions(requestURI);
	}

	private boolean isAcceptableRequest(String method, String route) {
		return routeExists() && (Arrays.asList(getRouteOptions()).contains(method));
	}

	public boolean routeHasParams() {
		return requestURI.contains("?") || (this.parameters != null);
	}
	
	public boolean isGetRoot() {
		return request.getMethod().equals("GET") && request.getURI().equals("/");
	}

	private void separateRouteFromParams(String query) {
		String[] routeParts = query.split("\\?", 2);
		this.requestURI = routeParts[0];
		this.parameters = routeParts[1];
	}

	public boolean isValidRedirectRequest() {
		String path = request.getURI();
		return path.equals("/redirect");
	}
	
	public boolean isValidOptionRequest() {
		String path = request.getURI();
		return path.equals("/method_options");
	}

	public boolean methodIsNotAllowed() {
		return (isFileRequest() && !(isAcceptableRequest(requestMethod, requestURI)));
	}

	public boolean isFileRequest() {
		return Arrays.asList(Routes.FILES).contains(request.getURI());
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

	public String[] separateParameters() {
		String params = request.getURI().split("/parameters?.")[1];
		params = params.replace("=", " = ");
		return params.split("&");
	}
}