package javaserver;

public class RequestParser {

	public static Request createRequest(String rawRequest) {
		String requestMethod = getRequestMethod(rawRequest);
		String requestURI = getRequestURI(rawRequest);
		return new Request(requestMethod, requestURI);
	}

	public static String getRequestMethod(String rawRequest) {
		return splitRequest(rawRequest)[0];
	}

	public static String getRequestURI(String rawRequest) {
		return splitRequest(rawRequest)[1];
	}

	private static String[] splitRequest(String rawRequest) {
		return rawRequest.split(" ");
	}
}
