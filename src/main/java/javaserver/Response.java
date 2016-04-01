package javaserver;

public class Response {

	private String statusLine;
	private String header;
	private String body;

	public Response(ResponseBuilder builder) {
		this.statusLine = builder.statusLine;
		this.header = builder.header;
		this.body = builder.body;
	}

	public String getResponseCode() {
		return statusLine;
	}

	public String getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}

	public String formatResponse() {
		String output = newLine(statusLine);
		if (header != null) {
			output += newLine(header);
		}
		output += newLine("");
		if (body != null) {
			output += newLine(body);
		}
		return output;
	}

	private String newLine(String line) {
		return line + System.lineSeparator();
	}
}
