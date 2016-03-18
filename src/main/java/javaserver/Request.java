package javaserver;

public class Request {
	private String method;
	private String URI;
	private String defaultRedirectLocation = "http://localhost:5000/";

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

	public String redirectLocation() {
		return this.defaultRedirectLocation;
	}
}

