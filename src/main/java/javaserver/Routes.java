package javaserver;

import java.io.File;
import java.util.HashMap;

public class Routes {
	private static final String[] SUPPORTED_ROOT_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] SUPPORTED_PARAMETERS_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
	private static final String[] SUPPORTED_FILE_REQUESTS = {"GET"};

	private static final String[] FOUND_REDIRECT_REQUESTS = {"GET"};

	public static HashMap<String, String[]> supportedRouteRequests;
	public static HashMap<String, String[]> foundRouteRequests;

	public static void configure() {
		supportedRouteRequests = new HashMap<String, String[]>();
		foundRouteRequests = new HashMap<String, String[]>();

		addSupportedRoute("/", SUPPORTED_ROOT_REQUESTS);
		addSupportedRoute("/form", SUPPORTED_FORM_REQUESTS);
		addSupportedRoute("/parameters", SUPPORTED_PARAMETERS_REQUESTS);
		addSupportedRoute("/method_options", SUPPORTED_METHOD_OPTIONS);

		for (String file : getDirectoryListing("public")) {
			addSupportedRoute("/" + file, SUPPORTED_FILE_REQUESTS);
		}

		addFoundRoute("/redirect", FOUND_REDIRECT_REQUESTS);
	}
	
	private static void addSupportedRoute(String route, String[] supportedMethods) {
		supportedRouteRequests.put(route, supportedMethods);
	}

	private static void addFoundRoute(String route, String[] supportedMethods) {
		foundRouteRequests.put(route, supportedMethods);
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
	
	public static String getPublicFileNames() {
		String listing = "";
		String[] publicFiles = getDirectoryListing("public");
		for (String file: publicFiles) {
			listing += file + System.lineSeparator();
		}
		return listing;
	}
	
	private static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
