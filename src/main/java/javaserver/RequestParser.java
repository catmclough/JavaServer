package javaserver;

public class RequestParser {
	public String getRequestType(String request) {
		return requestParts(request)[0];
	}

	public String getRequestURI(String request) {
		return requestParts(request)[1];
	}

	private String[] requestParts(String request) {
		return request.split(" ");
	}
}
