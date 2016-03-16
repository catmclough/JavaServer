package javaserver;

import java.io.UnsupportedEncodingException;

public class ResponseBuilder {
	private Request request;

	ResponseBuilder(Request request) {
		this.request = request;
	}

	public Response createResponse() {
		Response response = new Response();
		response.setResponseCode(getResponseCode());
		response.setHeader(getResponseHeader());
		response.setBody(getResponseBody());
		return response;
	}

	private String getResponseCode() {
		if (request.isOK()) {
			return HTTPStatusCodes.TWO_HUNDRED;
		} else {
			return HTTPStatusCodes.FOUR_OH_FOUR;
		}
	}

	private String getResponseHeader() {
		String header = new String();
		if (request.getURI().equals("/method_options")) {
			header += "Allow: ";
			header += String.join(",", Routes.getOptions(request));
		}
		return header;
	}

	private String getResponseBody() {
		String body = new String();
		if (request.hasVariableParams()) {
			String[] allParams = separateParameters();
			for (int i = 0; i < allParams.length; i++) {
				body += decode(allParams[i]) + System.lineSeparator();
			}
		}
		return body;
	}

	private String[] separateParameters() {
		String params = request.getURI().split("/parameters?.")[1];
		params = params.replace("=", " = ");
		return params.split("&");
	}
	
	private String decode(String parameter) {
		try {
			parameter = java.net.URLDecoder.decode(parameter, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("ResponseBuilder could not decode one or more of the request's parameters");
		}
		return parameter;
	}
}
