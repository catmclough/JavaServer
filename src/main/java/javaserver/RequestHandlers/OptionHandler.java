package javaserver.RequestHandlers;
import javaserver.Request;

public class OptionHandler extends RequestHandler {
	public String requestURI;
	
	public OptionHandler(Request request) {
		super(request);
		this.requestURI = request.getURI();
	}
	
	@Override
	public String[] getRouteOptions(String uri) {
		String[] supportedRequests = null;
		if (uri.equals("/method_options")) 
			supportedRequests = new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
		return supportedRequests;
	}
}
