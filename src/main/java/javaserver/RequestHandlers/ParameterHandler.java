package javaserver.RequestHandlers;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javaserver.Request;

public class ParameterHandler extends RequestHandler {
	String requestMethod;
	String requestURI;

	public ParameterHandler(Request request) {
		super(request);
		this.requestMethod = request.getMethod();
		this.requestURI = request.getURI();
	}

	@Override
	protected void configureMethodOptions() {
		addRoute("/parameters", new String[] {"GET"});
	}

	@Override
	public boolean requestIsSupported() {
		return super.routeExists(separateRoute()) && (Arrays.asList(getRouteOptions(separateRoute())).contains(requestMethod));
	}

	public String[] splitParameters() {
		String query = separateParams();
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

	private String separateRoute() {
		String[] routeParts = requestURI.split("\\?", 2);
		return routeParts[0];
	}

	private String separateParams() {
		String[] routeParts = requestURI.split("\\?", 2);
		return routeParts[1];
	}
}
