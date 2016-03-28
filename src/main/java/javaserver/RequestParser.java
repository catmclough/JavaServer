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
			return "";
		}
	}

	protected static String getURIWithoutParams(String uri) {
		if (requestHasParams(uri)) {
			String[] routeParts = uri.split("\\?", 2);
			return routeParts[0];
		} else {
			return uri;
		}
	}

	private static boolean requestHasParams(String uri) {
		return uri.contains("?");
	}

	private static String[] splitRequest(String rawRequest) {
		return rawRequest.split(" ");
	}
}
