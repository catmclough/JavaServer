package javaserver;

import java.util.HashMap;

public class RequestBuilder {

	public HashMap<String, String> getRequestObject(String rawRequest) {
		HashMap<String, String> request = new HashMap<>();
		request.put("Type", getRequestType(rawRequest));
		request.put("URI", getRequestURI(rawRequest));
		return request;
	}

	private String getRequestType(String rawRequest) {
		String[] splitRequest = rawRequest.split(" ");
		return splitRequest[0];
	}

	private String getRequestURI(String rawRequest) {
		String[] splitRequest = rawRequest.split(" ");
		return splitRequest[1];
	}
}
