package javaserver.RequestHandlers;

import javaserver.Request;
import javaserver.Routes;

public class FileHandler extends RequestHandler {

	public FileHandler(Request request) {
		super(request);
	}

	@Override
	protected void configureMethodOptions() {
		for (String fileName : Routes.getDirectoryListing("public")) {
			addRoute("/" + fileName, new String[] {"GET"});
		}
	}
}
