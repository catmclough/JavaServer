package javaserver;

import java.util.HashMap;

public class Routes {
	private static final String[] SUPPORTED_ROOT_REQUESTS = {"GET"};
	private static final String[] SUPPORTED_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] SUPPORTED_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

	public static HashMap<String, String[]> routeOptions;

	public static void configure() {
		routeOptions = new HashMap<String, String[]>();
		routeOptions.put("/", SUPPORTED_ROOT_REQUESTS);
		routeOptions.put("/form", SUPPORTED_FORM_REQUESTS);
		routeOptions.put("/method_options", SUPPORTED_METHOD_OPTIONS);
	}

	public static String[] getOptions(String route) {
		return routeOptions.get(route);
	}
}

