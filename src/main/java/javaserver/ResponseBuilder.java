package javaserver;

public class ResponseBuilder {
	protected String statusLine;
	protected String header;
	protected String body;

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

