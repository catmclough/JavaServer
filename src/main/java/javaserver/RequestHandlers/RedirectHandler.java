package javaserver.RequestHandlers;

import javaserver.Request;

public class RedirectHandler extends RequestHandler {
	
	public RedirectHandler(Request request) {
		super(request);
	}
	
	@Override
	protected void configureMethodOptions() {
		addRoute("/redirect", new String[] {"GET"});
	}
}
