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

	public String[] routeOptions() {
		return Routes.getOptions(URI);
	}

	public boolean hasVariableParams() {
		return URI.contains("?");
	}
	
	public boolean isRedirect() {
		return URI.contains("redirect");
	}

	public String redirectLocation() {
		return "http://localhost:5000/";
	}
	
	public boolean isFileRequest() {
		return Arrays.asList(Routes.FILES).contains(this.URI);
	}
}

