package javaserver;

import java.io.File;
import java.util.HashMap;

public class Routes {
	public static HashMap<String, String> acceptableRoutes = new HashMap<String, String>();

	public static void configure() {
		addRoute("/", "Directory Handler");
		addRoute("/parameters", "Parameter Handler");
		addRoute("/method_options", "Option Handler");
		addRoute("/form", "Default Handler");

		for (String file : getDirectoryListing("public")) {
			addRoute("/" + file, "File Handler");
		}

		addRoute("/redirect", "Redirect Handler");
	}
	
	private static void addRoute(String route, String requestHandler) {
		acceptableRoutes.put(route, requestHandler);
	}

	public static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
