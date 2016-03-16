package javaserver;

import java.util.Arrays;

public class Request {
	String method;
	String URI;

	Request(String method, String URI) {
		this.method = method;
		this.URI = URI;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return URI;
	}

	public boolean isOK() {
		String requestType = getMethod();
		if (routeOptions() != null && (Arrays.asList(routeOptions()).contains(requestType))) {
			return true;
		} else if (this.hasVariableParams()) {
			return true;
		} else {
			return false;
		}
	}

	public String[] routeOptions() {
		return Routes.getOptions(URI);
	}

	public boolean hasVariableParams() {
		return URI.contains("/parameters?");
	}
}
