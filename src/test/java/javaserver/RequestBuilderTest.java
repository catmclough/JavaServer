package javaserver;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class RequestBuilderTest {
	RequestBuilder testRequestBuilder;
	String mockRawRequest = "POST /users/123 HTTP/1.1\r\n";
	String mockRequestType = "POST";
	String mockRequestURL = "/users/123";
	
	HashMap <String, String> mockRequestObject = new HashMap<String, String>();
	
	@Before
	public void setUp() {
		this.testRequestBuilder = new RequestBuilder();
		mockRequestObject.put("Type", "POST");
		mockRequestObject.put("URI", "/users/123");
	}

	@Test
	public void testSimpleGetRequestObject() {
		assertEquals(testRequestBuilder.getRequestObject(mockRawRequest), mockRequestObject);
	}
}
