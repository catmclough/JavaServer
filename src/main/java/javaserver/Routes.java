package javaserver;

import java.io.File;
import java.util.HashMap;

public class Routes {
	public static HashMap<String, String> routeHandlers = new HashMap<String, String>(); 
	static {
		routeHandlers.put("/", "Directory Handler");
		routeHandlers.put("/parameters", "Parameter Handler");
		routeHandlers.put("/method_options", "Option Handler");
		routeHandlers.put("/form", "Form Handler");
		routeHandlers.put("/redirect", "Redirect Handler");
		for (String file : getDirectoryListing("public")) {
			routeHandlers.put("/" + file, "File Handler");
		}
	}

	public static HashMap<String, String[]> routeOptions = new HashMap<String, String[]>();
	static {
		routeOptions.put("/", new String[] {"GET"});
		routeOptions.put("/redirect", new String[] {"GET"});
		routeOptions.put("/parameters", new String[] {"GET"});
		routeOptions.put("/method_options", new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"});
		routeOptions.put("/form", new String[] {"POST", "PUT"});
	}

	public static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
		
