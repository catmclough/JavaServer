package javaserver;

public enum HTTPStatusCode {
	TWO_HUNDRED ("HTTP/1.1 200 OK"),
	THREE_OH_TWO ("HTTP/1.1 302 Found"),
	FOUR_OH_FOUR ("HTTP/1.1 404 Not Found");
	
	private final String statusLine;
	
	private HTTPStatusCode(String statusLine) {
		this.statusLine = statusLine;
	}
	
	public String getStatusLine() {
		return this.statusLine;
	}
}
