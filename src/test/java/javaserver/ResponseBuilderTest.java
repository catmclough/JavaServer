package javaserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResponseBuilderTest {
	ResponseBuilder testResponseBuilder;
	SocketWriter writer;
	Responder responder;
	
	String twoHundred = HTTPStatusCodes.TWO_HUNDRED;
	String fourOhFour = HTTPStatusCodes.FOUR_OH_FOUR;
	
	HashMap<String, String> request;

	String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	@Before
	public void setUp() {
		App.configureRoutes();
		request = new HashMap<String, String>();
		testResponseBuilder = new ResponseBuilder();
	}
	
	@After
	public void tearDown() {
		request.clear();
	}
	
	//TODO:
		//Test Response Has Response Code
		//Test Response Has Header Line
		//Test Response Has Blank Line
		//Test Response Has Body Line
	
	@Test
	public void testGetRootResponseCode() throws IOException {
		request.put("Type", "GET");
		request.put("URI", "/");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}

	@Test
	public void testGetFormResponseCode() throws IOException {
		request.put("Type", "GET");
		request.put("URI", "/form");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPostFormResponseCode() throws IOException {
		request.put("Type", "POST");
		request.put("URI", "/form");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPutFormResponseCode() throws IOException {
		request.put("Type", "PUT");
		request.put("URI", "/form");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		request.put("Type", "GET");
		request.put("URI", "/foo");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), fourOhFour);
	}
	
	@Test
	public void testMethodOptionsResponseCode() {
		request.put("Type", "GET");
		request.put("URI", "/method_options");
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}
	
	@Test
	public void testMethodOptionsHeader() throws IOException {
		request.put("Type", "GET");
		request.put("URI", "/method_options");
		testResponseBuilder.getResponse(request);
		String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
		assertEquals(testResponseBuilder.response.getHeader(), methodOptionsHeader);
	}
	
	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";
		request.put("Type", "GET");
		request.put("URI", codedURI);
		
		testResponseBuilder.getResponse(request);
		String responseBody = testResponseBuilder.response.getBody();
		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}
	
	@Test
	public void testCodedParamsGets200Response() {
		request.put("Type", "GET"); 
		request.put("URI", codedURI);
		testResponseBuilder.getResponse(request);
		assertEquals(testResponseBuilder.response.getResponseCode(), twoHundred);
	}

}
