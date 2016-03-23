package javaserver;

public class Request {
	private String method;
	private String URI;

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
	
	public boolean hasParams() {
		return URI.contains("?");
	}
	
	public String getURIWithoutParams() {
		String[] routeParts = URI.split("\\?", 2);
		return routeParts[0];
	}
}
