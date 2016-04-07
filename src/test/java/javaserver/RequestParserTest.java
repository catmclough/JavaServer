package javaserver;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestParserTest {

	private String rawRequest = "POST /users/123 HTTP/1.1";
	private String requestMethod = "POST";
	private String requestURI = "/users/123";
	private String requestWithRange = "GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4";
	private String partialData = "Range: bytes=0-4\n";
	private String uriWithParams = "GET /parameters?foo=bar";
	private String uriWithoutParams = "GET /parameters";

	@Test
	public void testParsesMethod() {
		assertEquals(RequestParser.getRequestMethod(rawRequest), requestMethod);
	}

	@Test
	public void testParsesURI() {
		assertEquals(RequestParser.getRequestURI(rawRequest), requestURI);
	}

	@Test
	public void testParsesData() {
		assertEquals(RequestParser.getRequestData(requestWithRange), partialData);
	}

	@Test
	public void testURIWithoutParams() {
		assertEquals(RequestParser.getURIWithoutParams(uriWithParams), uriWithoutParams);
	}
}
