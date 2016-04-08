package javaserver;

public class RequestParser {

	public static Request createRequest(String rawRequest) {
		String requestMethod = getRequestMethod(rawRequest);
		String requestURI = getRequestURI(rawRequest);
		String requestData = getRequestData(rawRequest);
		return new Request(requestMethod, requestURI, requestData);
	}
	
	public static String getRequestMethod(String rawRequest) {
		return rawRequest.split("\\s")[0];
	}

	public static String getRequestURI(String rawRequest) {
		try {
			return rawRequest.split("\\s")[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}

	public static String getRequestData(String rawRequest) {
		String data = "";
		try {
			String[] requestLines = rawRequest.split(System.lineSeparator());
			for (int i = 1; i < requestLines.length; i++) {
				data += requestLines[i] + System.lineSeparator();
			}
			return data;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return data;
	}
	
	public static String getCodedCredentials(Request request) {
	    return getAuthHeader(request.getData()).split("Authorization: Basic ")[1];
	}
	
	private static String getAuthHeader(String requestData) {
	    for (String dataLine : requestData.split(System.lineSeparator())) {
	        if (dataLine.startsWith("Authorization:")) {
	            return dataLine;
	        }
	    }
	    return null;
	}

	public static String getPartialRange(String requestData) {
		for (String dataLine : requestData.split(System.lineSeparator())) {
			if (dataLine.startsWith("Range:")) {
				return dataLine;
			}
		}
		return null;
	}

	public static String getURIWithoutParams(String uri) {
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
}