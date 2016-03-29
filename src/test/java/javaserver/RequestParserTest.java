package javaserver;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestParserTest {

	private String rawRequest = "POST /users/123 HTTP/1.1\r\n";
	private String requestMethod = "POST";
	private String requestURI = "/users/123";

	@Test
	public void testParsesMethod() {
		assertEquals(RequestParser.getRequestMethod(rawRequest), requestMethod);
	}

	@Test
	public void testParsesURI() {
		assertEquals(RequestParser.getRequestURI(rawRequest), requestURI);
	}
}
