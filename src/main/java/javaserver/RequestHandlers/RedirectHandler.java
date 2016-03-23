package javaserver.RequestHandlers;

import javaserver.Request;

public class RedirectHandler extends RequestHandler {
	
	public RedirectHandler(Request request) {
		super(request);
	}
	
	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/redirect")) 
			supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
