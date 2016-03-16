package javaserver;

import java.util.Arrays;

public class Request {
	String method;
	String URI;

	Request(String method, String URI) {
		this.method = method;
		this.URI = URI;
	}

	public String getMethod() {
		return method;
	}

	public String getURI() {
		return URI;
	}
	
	public boolean isOK() {
		String[] options = Routes.getOptions(this);
		String requestType = getMethod();
		if (options != null && (Arrays.asList(options).contains(requestType))) {
			return true;
		} else if (this.hasVariableParams()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasVariableParams() {
		return URI.contains("/parameters?");
	}
}
