package javaserver;

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
}
