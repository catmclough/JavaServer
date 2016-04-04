package javaserver;

public class Request {

	private String method;
	private String uri;

	public Request(String method, String uri) {
		this.method = method;
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return uri;
	}

	public boolean hasParams() {
		return uri.contains("?");
	}

	public String getURIWithoutParams() {
		String[] routeParts = uri.split("\\?", 2);
		return routeParts[0];
	}
}
