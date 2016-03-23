package javaserver.RequestHandlers;
import javaserver.Request;

public class FileHandler extends RequestHandler {
	
	public FileHandler(Request request) {
		super(request);
	}

	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
