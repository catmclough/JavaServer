package javaserver;

import static org.junit.Assert.*;
import org.junit.Test;

public class RequestParserTest {
	String rawRequest = "POST /users/123 HTTP/1.1\r\n";
	String requestMethod = "POST";
	String requestURI = "/users/123";

	@Test
	public void testParsesMethod() {
		assertEquals(RequestParser.getRequestMethod(rawRequest), requestMethod);
	}

	@Test
	public void testParsesURI() {
		assertEquals(RequestParser.getRequestURI(rawRequest), requestURI);
	}
	
	@Test
	public void handlesRequestWithNoURI() {
		String incompleteRequest = "GET";
		Request requestWithoutURI = RequestParser.createRequest(incompleteRequest);

		assertNotNull(requestWithoutURI);
		assertNotNull(requestWithoutURI.getURI());
		assertTrue(requestWithoutURI.getURI().isEmpty());
	}

	@Test
	public void handlesEmptyRequest() {
		String incompleteRequest = "";
		Request emptyRequest = RequestParser.createRequest(incompleteRequest);

		assertNotNull(emptyRequest);
		assertNotNull(emptyRequest.getMethod());
		assertTrue(emptyRequest.getMethod().isEmpty());
	}
}
