package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.DirectoryHandler;
import javaserver.HTMLContent;
import javaserver.HTTPStatusCode;
import javaserver.Routes;

public class DirectoryResponseBuilder extends ResponseBuilder {
	DirectoryHandler requestHandler;

	public DirectoryResponseBuilder(DirectoryHandler directoryRequestHandler) {
		super(directoryRequestHandler);
		this.requestHandler = directoryRequestHandler;
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
		response.setHeader("Content-Type: text/html;");
		response.setBody(getDirectoryLinksHTML());
	}

	@Override
	protected String getStatusLine() {
		HTTPStatusCode responseCode;
		if (requestHandler.requestIsSupported()) {
			responseCode = HTTPStatusCode.TWO_HUNDRED;
		} else {
			responseCode = HTTPStatusCode.FOUR_OH_FIVE;
		}
		return responseCode.getStatusLine();
	}

	private String getDirectoryLinksHTML() {
		return HTMLContent.openHTMLAndBody("Cat's Java Server") + HTMLContent.listOfLinks(getPublicFileNames()) + HTMLContent.closeBodyAndHTML();
	}

	private String[] getPublicFileNames() {
		String[] publicFiles = Routes.getDirectoryListing("public");
		return publicFiles;
	}
}
