package javaserver.RequestHandlers;

import javaserver.Request;

public class DirectoryHandler extends RequestHandler {
	
	public DirectoryHandler(Request request) {
		super(request);
	}

	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/")) 
			supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
