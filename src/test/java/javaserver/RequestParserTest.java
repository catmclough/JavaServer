package javaserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RequestParserTest {
	String mockRequest = "POST /users/123 HTTP/1.1\r\n";
	RequestParser testParser;
	
	String mockRequestType = "POST";
	String mockRequestURI = "/users/123";
	
	@Before
	public void setUp() {
		this.testParser = new RequestParser();
	}
	
	@Test
	public void testGetRequestType() {
		assertEquals(testParser.getRequestType(mockRequest), mockRequestType);
	}

	@Test
	public void testGetRequestURI() {
		assertEquals(testParser.getRequestURI(mockRequest), mockRequestURI);
	}
}
