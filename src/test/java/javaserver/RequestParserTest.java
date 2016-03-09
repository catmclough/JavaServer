package javaserver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RequestParserTest {
	String mockRequest = "POST /users/123 HTTP/1.1\r\n";
	RequestBuilder testRequestBuilder;
	
	String mockRequestType = "POST";
	String mockRequestURI = "/users/123";
	
	@Before
	public void setUp() {
		this.testRequestBuilder = new RequestBuilder();
	}
	
	@Test
	public void testGetRequestType() {
		assertEquals(testRequestBuilder.getRequestType(mockRequest), mockRequestType);
	}

	@Test
	public void testGetRequestURI() {
		assertEquals(testRequestBuilder.getRequestURI(mockRequest), mockRequestURI);
	}
}
