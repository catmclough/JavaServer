package javaserver;

import java.util.Arrays;
import java.util.HashMap;

public class Routes {
	private static String[] OK_ROOT_REQUESTS = {"GET"};
	private static String[] OK_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private static String[] OK_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

	public static HashMap<String, String[]> routeOptions;

	public static void setUp() {
		routeOptions = new HashMap<String, String[]>();
		routeOptions.put("/", OK_ROOT_REQUESTS);
		routeOptions.put("/form", OK_FORM_REQUESTS);
		routeOptions.put("/method_options", OK_METHOD_OPTIONS);
	}

	public static String[] getOptions(HashMap<String, String> request) {
		String route = request.get("URI");
		return routeOptions.get(route);
	}

	public static boolean isOK(HashMap<String, String> request) {
		String[] options = getOptions(request);
		String requestType = request.get("Type");
		if (options != null && (Arrays.asList(options).contains(requestType))) {
			return true;
		} else if (hasVariableParams(request.get("URI"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean hasVariableParams(String URI) {
		return URI.contains("/parameters?");
	}
}

