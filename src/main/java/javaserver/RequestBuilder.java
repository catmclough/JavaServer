package javaserver;

import java.util.HashMap;

public class RequestBuilder {
	
	public HashMap<String, String> buildRequest(String rawRequest) {
		HashMap<String, String> request = new HashMap<>();
		//add "Type" : request type
		request.put("Type", getRequestType(rawRequest));
		//add "URI" " requestURI
		request.put("URI", getRequestURI(rawRequest));
		//add "Data" : requestData
		return request;
	}
	
	public String getRequestType(String rawRequest) {
		String[] splitRequest = rawRequest.split(" ");
		return splitRequest[0];
	}

	public String getRequestURI(String rawRequest) {
		String[] splitRequest = rawRequest.split(" ");
		return splitRequest[1];
	}
}
