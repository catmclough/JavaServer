package javaserver.RequestHandlers;
import javaserver.Request;

public class FileHandler extends RequestHandler {
	String URI;
	
	public FileHandler(Request request) {
		super(request);
		this.URI = request.getURI();
	}

	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = new String[] {"GET"};
		return supportedRequests;
	}
}
