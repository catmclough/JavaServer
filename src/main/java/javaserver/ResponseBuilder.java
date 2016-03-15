package javaserver;

import java.util.Arrays;
import java.util.HashMap;

public class ResponseBuilder {
	private String[] OK_ROOT_REQUESTS = {"GET"};
	private String[] OK_FORM_REQUESTS = {"GET", "POST", "PUT"};
	private String[] OK_METHOD_OPTIONS = {"GET", "HEAD", "POST", "OPTIONS", "PUT"};

	protected HashMap<String, String> responseParts;
	protected HashMap<String, String> responseCodes;
	protected HashMap<String, String[]> okRequests;

	ResponseBuilder() {
		this.responseParts = new HashMap<String, String>();
		this.okRequests = new HashMap<String, String[]>();
		setOKRequests();

		this.responseCodes = new HashMap<String, String>();
		setResponseCodes();
	}

	private void setOKRequests() {
		okRequests.put("/", OK_ROOT_REQUESTS);
		okRequests.put("/form", OK_FORM_REQUESTS);
		okRequests.put("/method_options", OK_METHOD_OPTIONS);
	}

	private void setResponseCodes() {
		responseCodes.put("200", "HTTP/1.1 200 OK");
		responseCodes.put("404", "HTTP/1.1 404 Not Found");
	}

	public String getResponse(HashMap<String, String> request) {
		setResponseParts(request);
		String response = responseParts.get("Response Code");
		response += responseParts.get("Header");
		return response;
	}

	private void setResponseParts(HashMap<String, String> request) {
		this.responseParts.put("Response Code", getResponseCode(request));
		this.responseParts.put("Header", getResponseHeader(request));
	}

	private String getResponseCode(HashMap<String, String> request) {
		String[] methodOptions = okRequests.get(request.get("URI"));
		String requestType = request.get("Type");
		if (methodOptions != null && (Arrays.asList(methodOptions).contains(requestType))) {
			return responseCodes.get("200");
		} else {
			return responseCodes.get("404");
		}
	}

	private String getResponseHeader(HashMap<String, String> request) {
		String header = "";
		if (request.get("URI").equals("/method_options")) {
			header += "\r\nAllow: ";
			header += String.join(",", okRequests.get("/method_options"));
			header += "\r\n";
		}
		return header;
	}
}
