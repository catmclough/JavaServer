package javaserver;

import java.util.Arrays;
import java.util.HashMap;

public class Routes {
	private static final String[] OK_ROOT_REQUESTS = {"GET"};
	private static final String[] OK_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static final String[] OK_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

	public static HashMap<String, String[]> routeOptions;

	public static void setUp() {
		routeOptions = new HashMap<String, String[]>();
		routeOptions.put("/", OK_ROOT_REQUESTS);
		routeOptions.put("/form", OK_FORM_REQUESTS);
		routeOptions.put("/method_options", OK_METHOD_OPTIONS);
	}

	public static String[] getOptions(Request request) {
		String route = request.getURI();
		return routeOptions.get(route);
	}

	public static boolean isOK(Request request) {
		String[] options = getOptions(request);
		String requestType = request.getMethod();
		if (options != null && (Arrays.asList(options).contains(requestType))) {
			return true;
		} else if (hasVariableParams(request.getURI())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean hasVariableParams(String URI) {
		return URI.contains("/parameters?");
	}
}

