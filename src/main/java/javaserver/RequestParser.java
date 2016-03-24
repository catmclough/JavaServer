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
		try {
			return splitRequest(rawRequest)[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid request. HTTP requests must have a method and URI.");
			return "";
		}
	}

	private static String[] splitRequest(String rawRequest) {
		return rawRequest.split(" ");
	}
}
