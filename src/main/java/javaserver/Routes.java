package javaserver;

import java.util.HashMap;

public class Routes {
	public static final String[] FILES = {"/file1", "/text-file.txt"};

	private static final String[] SUPPORTED_ROOT_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] SUPPORTED_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
	private static final String[] SUPPORTED_FILE_REQUESTS = {"GET"};

	private static final String[] FOUND_REDIRECT_REQUESTS = {"GET"};

	public static HashMap<String, String[]> supportedRouteRequests;
	public static HashMap<String, String[]> foundRouteRequests;

	public static void configure() {
		supportedRouteRequests = new HashMap<String, String[]>();
		supportedRouteRequests.put("/", SUPPORTED_ROOT_REQUESTS);
		supportedRouteRequests.put("/form", SUPPORTED_FORM_REQUESTS);
		supportedRouteRequests.put("/method_options", SUPPORTED_METHOD_OPTIONS);
		for (String fileRoute : FILES) {
			supportedRouteRequests.put(fileRoute, SUPPORTED_FILE_REQUESTS);
		}

		foundRouteRequests = new HashMap<String, String[]>();
		foundRouteRequests.put("/redirect", FOUND_REDIRECT_REQUESTS);
	}

	public static String[] getOptions(String route) {
		if (supportedRouteRequests.get(route) != null) {
			return supportedRouteRequests.get(route); 
		} else if (foundRouteRequests.get(route) != null) {
			return foundRouteRequests.get(route);
		} else {
			return null;
		}
	}
}