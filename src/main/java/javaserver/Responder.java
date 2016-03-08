package javaserver;

import java.io.IOException;

public class Responder {
	
	public final String TWO_HUNDRED = "HTTP/1.1 200 OK";
	public final String FOUR_OH_FOUR = "HTTP/1.1 404 Not Found";
	private String getRequest = "GET";
	private String postRequest = "POST";
	private String putRequest = "PUT";
	private String root = "/";
	private String form = "/form";
	private String response;

	public String respond(String requestType, String requestURI, SocketWriter writer) {
		if (requestURI.equals(root)) {
			if (requestType.equals(getRequest)) {
				this.response = TWO_HUNDRED;
			} else {
				this.response = FOUR_OH_FOUR;
			}
		} else if (requestURI.equals(form)) {
			if (requestType.equals(postRequest) || requestType.equals(putRequest)) {
				this.response = TWO_HUNDRED;
			}
			else {
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
