package javaserver;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Responder {
	
	public final String TWO_HUNDRED = "HTTP/1.1 200 OK";
	public final String FOUR_OH_FOUR = "HTTP/1.1 404 Not Found";
	private String response;
	private HashMap<String, String[]> acceptableRequests;
	private String[] rootRequests = {"GET"};
	private String[] formRequests = {"POST", "PUT"};
	
	Responder() {
		this.acceptableRequests = new HashMap<String, String[]>();
		setAcceptableRequests();	
	}
	
	private void setAcceptableRequests() {
		acceptableRequests.put("/", rootRequests);
		acceptableRequests.put("/form", formRequests);
	}
	
	public String respond(HashMap<String, String> request, SocketWriter writer) {
		String requestType = request.get("Type");
		String requestURI = request.get("URI");
		String[] dems = acceptableRequests.get(requestURI);
		if (dems != null && (Arrays.asList(dems).contains(requestType))) {
			this.response = TWO_HUNDRED;
		} else {
			this.response = FOUR_OH_FOUR;
		}
		
		try {
		  writer.respond(response);
		} catch (IOException e) {
		  System.out.println("Error caught whilst responding to Client");
		}
		return response;
	}
}
