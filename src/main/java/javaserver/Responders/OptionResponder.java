package javaserver.ResponseBuilders;

import java.util.Arrays;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.Response;
import javaserver.ResponseBuilder;

public class OptionResponder implements Responder {

	private String[] supportedMethods;

	public OptionResponder(String[] supportedMethods) {
		this.supportedMethods = supportedMethods;
	}

	@Override
	public Response getResponse(Request request) {
		return new ResponseBuilder()
		.statusLine(getStatusLine(request))
		.header(getResponseHeader(request))
		.build();
	}

	@Override
	public String getStatusLine(Request request) {
		if (requestIsSupported(request.getMethod())) {
			return HTTPStatusCode.TWO_HUNDRED.getStatusLine();
		} else {
			return HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
		}
	}

	private boolean requestIsSupported(String method) {
		return Arrays.asList(supportedMethods).contains(method);
	}

	private String getResponseHeader(Request request) {
		String header = new String();
		if (requestIsSupported(request.getMethod())) {
			header += "Allow: ";
			header += String.join(",", supportedMethods);
		}
		return header;
	}
}
