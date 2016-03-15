package javaserver;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class ResponseBuilder {
	private HashMap<String, String> request;
	
	protected HashMap<String, String> response;

	ResponseBuilder() {
		this.response = new HashMap<String, String>();
	}

	public String getResponse(HashMap<String, String> request) {
		this.request = request;
		setResponseParts();
		String output = response.get("Response Code") + System.lineSeparator();
		output += response.get("Header") + System.lineSeparator();
		output += System.lineSeparator();
		output += response.get("Body") + System.lineSeparator();
		return output;
	}

	private void setResponseParts() {
		response.put("Response Code", getResponseCode(request));
		response.put("Header", getResponseHeader(request));
		response.put("Body", getResponseBody(request));
	}

	private String getResponseCode(HashMap<String, String> request) {
		if (Routes.isOK(request)) {
			return HTTPStatusCodes.TWO_HUNDRED;
		} else {
			return HTTPStatusCodes.FOUR_OH_FOUR;
		}
	}

	private String getResponseHeader(HashMap<String, String> request) {
		String header = new String();
		if (request.get("URI").equals("/method_options")) {
			header += "Allow: ";
			header += String.join(",", Routes.getOptions(request));
		}
		return header;
	}
	
	private String getResponseBody(HashMap<String, String> request) {
		String body = new String();
		if (Routes.hasVariableParams(request.get("URI"))) {
			String params = request.get("URI").split("/parameters?.")[1];
			params = params.replace("=", " = ");
			String[] allParams = params.split("&");
			for (int i=0; i<allParams.length; i++) {
				body += decode(allParams[i]) + System.lineSeparator();
			}
		}
		return body;
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
