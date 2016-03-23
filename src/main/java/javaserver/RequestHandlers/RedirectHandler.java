package javaserver.RequestHandlers;

import javaserver.Request;

public class RedirectHandler extends RequestHandler {
	String URI;
	
	public RedirectHandler(Request request) {
		super(request);
		this.URI = request.getURI();
	}
	
	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/redirect")) 
			supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
