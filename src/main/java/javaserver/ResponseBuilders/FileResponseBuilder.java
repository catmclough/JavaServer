package javaserver.ResponseBuilders;

import javaserver.RequestHandlers.FileHandler;
import javaserver.HTTPStatusCode;

public class FileResponseBuilder extends ResponseBuilder {
	FileHandler requestHandler;

	public FileResponseBuilder(FileHandler fileRequestHandler) {
		super(fileRequestHandler);
		this.requestHandler = fileRequestHandler;
	}

	@Override
	protected void setResponseData() {
		response.setStatusLine(getStatusLine());
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
}
