package javaserver.RequestHandlers;
import javaserver.Request;

public class OptionHandler extends RequestHandler {
	public String requestURI;
	
	public OptionHandler(Request request) {
		super(request);
		this.requestURI = request.getURI();
	}
	
	@Override
	protected void configureMethodOptions() {
		addRoute("/method_options", new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"});
	}
}
