package javaserver;

public class Response {
	private String statusLine;
	private String header;
	private String body;

	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	public String getResponseCode() {
		return statusLine;
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

	public String formatResponse() {
		String output = statusLine + System.lineSeparator();
		if (header != null) {
			output += header + System.lineSeparator();
		}
		output += System.lineSeparator();
		if (body != null) {
			output += body + System.lineSeparator();
		}
		return output;
	}
}
