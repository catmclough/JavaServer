package javaserver.RequestHandlers;

import java.util.Arrays;
import java.util.HashMap;

import javaserver.Request;

public class RequestHandler {
	private String requestMethod;
	private String requestURI;
	protected HashMap<String, String[]> supportedRequests = new HashMap<String, String[]>();

	public RequestHandler(Request request) {
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
		configureMethodOptions();
	}

	protected void configureMethodOptions() {
		supportedRequests.put("/form", new String[] {"POST", "PUT"});
	}

	public void addRoute(String route, String[] methodOptions) {
		supportedRequests.put(route, methodOptions);
	}

	public boolean requestIsSupported() {
		String[] routeOptions = getRouteOptions(requestURI);
		return routeExists(requestURI) && (Arrays.asList(routeOptions).contains(requestMethod));
	}

	protected boolean routeExists(String uri) {
		return getRouteOptions(uri) != null;
	}

	public String[] getRouteOptions(String uri) {
		return supportedRequests.get(uri);
	}
}
