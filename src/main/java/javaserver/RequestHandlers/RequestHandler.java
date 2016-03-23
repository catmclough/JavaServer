package javaserver.RequestHandlers;

import java.util.Arrays;

import javaserver.Request;

public class RequestHandler {
	private String requestMethod;
	private String requestURI;
	
	public RequestHandler(Request request) {
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
	}
	
	public boolean requestIsSupported() {
		String[] routeOptions = getRouteOptions(requestURI);
		return routeExists(requestURI) && (Arrays.asList(routeOptions).contains(requestMethod));
	}

	protected boolean routeExists(String uri) {
		return getRouteOptions(uri) != null;
	}

	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/form")) 
			supportedRequests = new String[] {"POST", "PUT"};
		return supportedRequests;
	}
}