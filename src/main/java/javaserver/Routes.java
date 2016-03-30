package javaserver;

import java.io.File;
import java.util.HashMap;

import javaserver.ResponseBuilders.*;

public class Routes {

	public static HashMap<String, ResponseBuilder> routeResponders = new HashMap<String, ResponseBuilder>();
	static {
		routeResponders.put("/", new DirectoryResponseBuilder());
		routeResponders.put("/parameters", new ParameterResponseBuilder());
		routeResponders.put("/method_options", new OptionResponseBuilder());
		routeResponders.put("/form", new FormResponseBuilder());
		routeResponders.put("/redirect", new RedirectResponseBuilder());
		for (String file : getDirectoryListing("public")) {
			routeResponders.put("/" + file, new FileResponseBuilder());
		}
	}

	public static HashMap<String, String[]> routeOptions = new HashMap<String, String[]>();
	static {
		routeOptions.put("/", new String[] {"GET"});
		routeOptions.put("/redirect", new String[] {"GET"});
		routeOptions.put("/parameters", new String[] {"GET"});
		routeOptions.put("/method_options", new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"});
		routeOptions.put("/form", new String[] {"POST", "PUT"});
		for (String file : getDirectoryListing("public")) {
			routeOptions.put("/" + file, new String[] {"GET"});
		}
	}

	public static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
