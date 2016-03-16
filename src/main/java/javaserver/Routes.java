package javaserver;

import java.util.HashMap;

public class Routes {
	private static final String[] OK_ROOT_REQUESTS = {"GET"};
	private static final String[] OK_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] OK_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

	public static HashMap<String, String[]> routeOptions;

	public static void configure() {
		routeOptions = new HashMap<String, String[]>();
		routeOptions.put("/", OK_ROOT_REQUESTS);
		routeOptions.put("/form", OK_FORM_REQUESTS);
		routeOptions.put("/method_options", OK_METHOD_OPTIONS);
	}

	public static String[] getOptions(String route) {
		return routeOptions.get(route);
	}

	
}

