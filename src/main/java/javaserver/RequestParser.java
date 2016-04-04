package javaserver;

public class RequestParser {

	public static Request createRequest(String rawRequest) {
		String requestMethod = getRequestMethod(rawRequest);
		String requestURI = getRequestURI(rawRequest);
		String requestData = getRequestData(rawRequest);
		return new Request(requestMethod, requestURI, requestData);
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

	public static String getRequestData(String rawRequest) {
		String data = "";
		try {
			String[] requestLines = splitRequest(rawRequest);
			for (int i = 2; i < requestLines.length; i++) {
				if (requestLines[i].contains("=")) {
					data += requestLines[i] + "\n";
				}
			}
			return data;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return data;
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
		return rawRequest.split("\\s|\n");
	}
}
