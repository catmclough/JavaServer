package javaserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Responder {
	private String[] ACCEPTABLE_ROOT_REQUESTS = {"GET"};	
	private String[] ACCEPTABLE_FORM_REQUESTS = {"POST", "PUT"};
	private String TWO_HUNDRED = "HTTP/1.1 200 OK";
	private String FOUR_OH_FOUR = "HTTP/1.1 404 Not Found";

	protected HashMap<String, String> responseCodes; 
	protected HashMap<String, String[]> acceptableRequests;

	Responder() {
		this.acceptableRequests = new HashMap<String, String[]>();
		setAcceptableRequests();	
		
		this.responseCodes = new HashMap<String, String>();
		setResponseCodes();
	}
	
	private void setAcceptableRequests() {
		acceptableRequests.put("/", ACCEPTABLE_ROOT_REQUESTS);
		acceptableRequests.put("/form", ACCEPTABLE_FORM_REQUESTS);
	}
	
	private void setResponseCodes() {
		responseCodes.put("200", TWO_HUNDRED);
		responseCodes.put("404", FOUR_OH_FOUR);
	}
	
	public String respond(HashMap<String, String> request, SocketWriter writer) {
		String requestType = request.get("Type");
		String requestURI = request.get("URI");
		String response = getResponse(requestType, requestURI);
		try {
		  writer.respond(response);
		} catch (IOException e) {
		  System.out.println("Error caught as Responder attempts to respond to client");
		}
		return response;
	}
	
	private String getResponse(String requestType, String requestURI) {
		String response;
		String[] relevantRequests = acceptableRequests.get(requestURI);
		if (relevantRequests != null && (Arrays.asList(relevantRequests).contains(requestType))) {
			response = responseCodes.get("200");
		} else {
			response = responseCodes.get("404");
		}
		return response;
	}
}
