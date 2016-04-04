package javaserver;

public class Request {

	private String method;
	private String uri;
	private String data;

	public Request(String method, String uri, String data) {
		this.method = method;
		this.uri = uri;
		this.data = data;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return uri;
	}

	public String getData() {
		return data;
	}

	public boolean hasParams() {
		return uri.contains("?");
	}

	public String getURIWithoutParams() {
		String[] routeParts = uri.split("\\?", 2);
		return routeParts[0];
	}
}
