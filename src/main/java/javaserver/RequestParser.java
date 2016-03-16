package javaserver;

public class RequestParser {
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
