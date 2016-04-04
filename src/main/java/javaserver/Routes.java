package javaserver;

import java.util.HashMap;
import javaserver.ResponseBuilders.*;

public class Routes {

	private static HashMap<String, Responder> routeResponders = new HashMap<String, Responder>();
	static {
		routeResponders.put("/", new DirectoryResponder(new String[] {"GET"}, App.publicDirectory));
		routeResponders.put("/parameters", new ParameterResponder(new String[] {"GET"}));
		routeResponders.put("/method_options", new OptionResponder(new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"}));
		routeResponders.put("/form", new FormResponder(new String[] {"POST", "PUT"}));
		routeResponders.put("/redirect", new RedirectResponder(new String[] {"GET"}));
		for (String file : App.publicDirectory.getDirectoryListing()) {
			routeResponders.put("/" + file, new FileResponder(new String[] {"GET"}));
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
}
