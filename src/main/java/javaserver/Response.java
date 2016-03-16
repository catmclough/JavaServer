package javaserver;

public class Response {
	private String responseCode;
	private String header;
	private String body;

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseCode() {
		return responseCode;
	}
	
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getHeader() {
		return header;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}
	
	public String getFormattedResponse() {
		String output = getResponseCode() + System.lineSeparator();
		output += getHeader() + System.lineSeparator();
		output += System.lineSeparator();
		output += getBody() + System.lineSeparator();
		return output;
	}
}
