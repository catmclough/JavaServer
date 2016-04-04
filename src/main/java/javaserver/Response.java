package javaserver;

public class Response {

	private String statusLine;
	private String header;
	private String body;

	private Response(ResponseBuilder builder) {
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

	public static class ResponseBuilder {
		protected String statusLine;
		protected String header;
		protected String body;
		
		public ResponseBuilder(String statusLine) {
			this.statusLine = statusLine;
		}

		public ResponseBuilder statusLine(String statusLine) {
			this.statusLine = statusLine;
			return this;
		}

		public ResponseBuilder header(String header) {
			this.header = header;
			return this;
		}

		public ResponseBuilder body(String body) {
			this.body = body;
			return this;
		}

		public Response build() {
			return new Response(this);
		}
	}
}
