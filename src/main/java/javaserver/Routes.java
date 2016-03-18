package javaserver;

import java.util.HashMap;

public class Routes {
	public static final String[] FILES = {"/file1", "/text-file.txt"};

	private static final String[] SUPPORTED_ROOT_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] SUPPORTED_REDIRECT_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
	private static final String[] SUPPORTED_FILE_REQUESTS = {"GET"};

	public static HashMap<String, String[]> routeOptions;

	public static void configure() {
		routeOptions = new HashMap<String, String[]>();
		routeOptions.put("/", SUPPORTED_ROOT_REQUESTS);
		routeOptions.put("/form", SUPPORTED_FORM_REQUESTS);
		routeOptions.put("/method_options", SUPPORTED_METHOD_OPTIONS);
		routeOptions.put("/redirect", SUPPORTED_REDIRECT_REQUESTS);
		for (String fileRoute : FILES) {
			routeOptions.put(fileRoute, SUPPORTED_FILE_REQUESTS);
		}
	}

	public static String[] getOptions(String route) {
		return routeOptions.get(route);
	}
}