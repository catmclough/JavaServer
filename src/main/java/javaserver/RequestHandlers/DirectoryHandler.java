package javaserver.RequestHandlers;

import javaserver.Request;

public class DirectoryHandler extends RequestHandler {
	String URI;
	
	public DirectoryHandler(Request request) {
		super(request);
		this.URI = request.getURI();
	}

	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/")) 
			supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
