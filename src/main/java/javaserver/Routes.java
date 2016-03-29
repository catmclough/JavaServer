package javaserver;

import java.io.File;
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

	public static String getPublicFileNames() {
		String listing = "";
		String[] publicFiles = getDirectoryListing("public");
		for (String file: publicFiles) {
			listing += file + System.lineSeparator();
		}
		return listing;
	}

	public static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
