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
		response.setBody(getHTMLDirectoryLinks());
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

	private String getHTMLDirectoryLinks() {
		return HTMLContent.openPageAndBody() + HTMLContent.listOfLinks(getPublicFileNames()) + HTMLContent.closeOfBodyAndPage();
	}

	private String[] getPublicFileNames() {
		String[] publicFiles = Routes.getDirectoryListing("public");
		return publicFiles;
	}
}
