package javaserver;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class RequestHandler {
	private Request request;
	private String requestMethod;
	private String requestURI;
	
	RequestHandler(Request request) {
		this.request = request;
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
	}
	
	public boolean requestIsSupported() {
		return (routeExists(requestURI) && (isAcceptableRequest(requestMethod, requestURI)) || isAcceptableRequestWithParams());
	}

	private boolean routeExists(String route) {
		return Routes.getOptions(route) != null;
	}

	private boolean isAcceptableRequest(String method, String route) {
		return (Arrays.asList(Routes.getOptions(route)).contains(method));
	}

	public boolean isGetRoot() {
		return request.getMethod().equals("GET") && request.getURI().equals("/");
	}

	private boolean isAcceptableRequestWithParams() {
		String routeWithoutParams = getRouteWithoutParams(requestMethod);
		return routeExists(routeWithoutParams) && isAcceptableRequest(requestMethod, routeWithoutParams);
	}
	
	private String getRouteWithoutParams(String query) {
		String route = query;
		if (query.contains("?"))
			route = query.split(".*?.")[0];
		System.out.println(query);
		return route;
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