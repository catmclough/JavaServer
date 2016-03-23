package javaserver.RequestHandlers;

import javaserver.Request;

public class DirectoryHandler extends RequestHandler {
	public DirectoryHandler(Request request) {
		super(request);
	}

	@Override
	protected void configureMethodOptions() {
		addRoute("/", new String[] {"GET"});
	}
}
