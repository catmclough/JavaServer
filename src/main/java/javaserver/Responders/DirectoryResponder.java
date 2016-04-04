package javaserver.ResponseBuilders;

import java.util.Arrays;
import javaserver.App;
import javaserver.HTMLContent;
import javaserver.HTTPStatusCode;
import javaserver.PublicDirectory;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class DirectoryResponder implements Responder {

	private String[] supportedMethods;
	private PublicDirectory directory;
	private String htmlHeader = "Content-Type: text/html;";

	public DirectoryResponder(String[] supportedMethods, PublicDirectory directory) {
		this.supportedMethods = supportedMethods;
		this.directory = directory;
	}

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.header(htmlHeader)
		.body(getDirectoryLinksHTML())
		.build();
	}


	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(supportedMethods).contains(method);
	}

	private String getDirectoryLinksHTML() {
		return HTMLContent.openHTMLAndBody(App.name) + HTMLContent.listOfLinks(directory.getDirectoryListing()) + HTMLContent.closeBodyAndHTML();
	}
}
