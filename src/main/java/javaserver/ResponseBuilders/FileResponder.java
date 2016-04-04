package javaserver.ResponseBuilders;

import java.util.Arrays;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class FileResponder implements Responder {

	private String[] supportedMethods;

	public FileResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();
		}
	}

	private boolean requestIsSupported(String method) {
		return Arrays.asList(supportedMethods).contains(method);
	}
}
