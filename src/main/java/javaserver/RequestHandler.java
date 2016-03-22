package javaserver;

import java.util.Arrays;

public class RequestHandler {
	private Request request;
	private String requestMethod;
	private String requestURI;
	private ParameterHandler paramHandler;
	
	RequestHandler(Request request) {
		this.request = request;
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
	}
	
	public boolean requestIsSupported() {
		if (routeHasParams()) {
			this.paramHandler = new ParameterHandler(request);
			return isAcceptableRequestWithParams();
		} else {
			return isAcceptableRequest(requestMethod, requestURI); 
		}
	}

	private boolean routeExists(String route) {
		return getRouteOptions(route) != null;
	}
	
	public String[] getRouteOptions() {
		return Routes.getOptions(requestURI);
	}
	public String[] getRouteOptions(String route) {
		return Routes.getOptions(route);
	}

	private boolean isAcceptableRequestWithParams() {
		String simplifiedRoute = paramHandler.separateRoute(requestURI);
		return isAcceptableRequest(requestMethod, simplifiedRoute);
	}

	private boolean isAcceptableRequest(String method, String route) {
		return routeExists(route) && (Arrays.asList(getRouteOptions(route)).contains(method));
	}

	public boolean routeHasParams() {
		return requestURI.contains("?");
	}
	
	public boolean isDirectoryRequest() {
		return request.getMethod().equals("GET") && request.getURI().equals("/");
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
		return Routes.getPublicFileNames().contains(request.getURI().substring(1));
	}

	public String[] getDecodedParameters() {
		String[] allParams = paramHandler.splitParameters(requestURI);
		for (int i = 0; i < allParams.length; i++) {
			allParams[i] = paramHandler.decodeParameters(allParams[i]);
		}
		return allParams;
	}
}