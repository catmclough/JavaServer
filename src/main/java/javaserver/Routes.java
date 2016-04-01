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

	public static Responder getResponder(String route) {
		Responder responder = routeResponders.get(route);
		if (responder == null) {
			return new ErrorResponder();
		} else {
			return responder;
		}
	}

	public static String[] getDirectoryListing(String directoryName) {
		File directory = new File(directoryName);
		return directory.list();
	}
}
