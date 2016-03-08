package javaserver;

import java.io.IOException;

public class Responder {
	
	public final String TWO_HUNDRED = "HTTP/1.1 200 OK";
	public final String FOUR_OH_FOUR = "HTTP/1.1 404 Not Found";
	private String getRequest = "GET";
	private String postRequest = "POST";
	private String root = "/";
	private String form = "/form";
	private String response;

	public String respond(String requestType, String requestURI, SocketWriter writer) {
		if (requestType.equals(getRequest)) {
			if (requestURI.equals(root)) {
				this.response = TWO_HUNDRED;
			} else {
				this.response = FOUR_OH_FOUR;
			}
		} else if (requestType.equals(postRequest)) {
			if (requestURI.equals(form)) {
				this.response = TWO_HUNDRED;
			} else {
				this.response = FOUR_OH_FOUR;
			}
		} else {
		  this.response = FOUR_OH_FOUR;
		}

		try {
		  writer.respond(response);
		} catch (IOException e) {
		  System.out.println("Error caught whilst trying to respond to Client");
		}
		return response;
	}
}
