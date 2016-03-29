package javaserver.ResponseBuilders;

import java.util.Arrays;

import javaserver.HTMLContent;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.Routes;

public class DirectoryResponseBuilder implements ResponseBuilder {

	private Response response;
	private String directory = "public";

	@Override
	public Response getResponse(Request request) {
		this.response = new Response();
		setResponseData(request);
		return this.response;
  }

	@Override
	public void setResponseData(Request request) {
		response.setStatusLine(getStatusLine(request));
		response.setHeader("Content-Type: text/html;");
		response.setBody(getDirectoryLinksHTML());
	}

	@Override
	public String getStatusLine(Request request) {
		HTTPStatusCode responseCode;
		if (requestIsSupported(request.getMethod(), request.getURI())) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}

	private boolean requestIsSupported(String method, String requestURI) {
		return Arrays.asList(Routes.routeOptions.get(requestURI)).contains(method);
	}

	private String getDirectoryLinksHTML() {
		return HTMLContent.openHTMLAndBody("Cat's Java Server") + HTMLContent.listOfLinks(Routes.getDirectoryListing(directory)) + HTMLContent.closeBodyAndHTML();
	}
}
