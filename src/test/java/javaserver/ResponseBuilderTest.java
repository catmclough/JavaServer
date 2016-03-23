package javaserver;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import javaserver.ResponseBuilders.ResponseBuilder;

public class ResponseBuilderTest {
	ResponseBuilder testResponseBuilder;
	SocketWriter writer;
	ResponseBuilder responder;
	RequestParser requestParser;

	String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
	String redirectHeader = "Location: http://localhost:5000/";

	String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	String threeOhTwo = HTTPStatusCode.THREE_OH_TWO.getStatusLine();
	String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	String fourOhFive= HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();

	@Before
	public void setUp() {
		App.configureRoutes();
	}
	
	private Response createResponse(String requestMethod, String route) {
		Request request = new Request(requestMethod, route);
		ResponseBuilder responder = ResponseBuilderFactory.createResponder(request);
		return responder.getResponse();
	}

	@Test
	public void testRespondsWith200() {
		Response response = createResponse("GET", "/"); 
		assertEquals(response.getResponseCode(), twoHundred);
	} 

	@Test
	public void testRedirectRespondsWith302() {
		Response response = createResponse("GET", "/redirect");
		assertEquals(response.getResponseCode(), threeOhTwo);
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		Response unsupportedRequestResponse = createResponse("GET", "/foo");
		assertEquals(unsupportedRequestResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testSimplePost() {
		Response postFormResponse = createResponse("POST", "/form");
		assertEquals(postFormResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testSimplePut() {
		Response postFormResponse = createResponse("PUT", "/form");
		assertEquals(postFormResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testMethodNotAllowedResponseCode() throws IOException {
		Response unallowedRequestResponse = createResponse("POST", "/file1");
		assertEquals(unallowedRequestResponse.getResponseCode(), fourOhFive);
	}

	@Test
	public void testOptionsResponseCode() throws IOException {
		Response optionsResponse = createResponse("GET", "/method_options");
		assertEquals(optionsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testOptionsHeader() throws IOException {
		Response optionsResponse = createResponse("GET", "/method_options");
		assertEquals(optionsResponse.getHeader(), methodOptionsHeader);
	}

	@Test
	public void testRedirectHeader() throws IOException {
		Response redirectResponse = createResponse("GET", "/redirect");
		assertEquals(redirectResponse.getHeader(), redirectHeader);
	}

	@Test
	public void testValidCodedParameters200() {
		Response validParamsResponse = createResponse("GET", codedURI);
		assertEquals(validParamsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testInvalidCodedParams404() {
		Response invalidParamResponse = createResponse("GET", "/foo?");
		assertEquals(invalidParamResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		Response validRequestWithCodedParamResponse = createResponse("GET", codedURI);
		String responseBody = validRequestWithCodedParamResponse.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}
	
	@Test
	public void testDirectoryLinks() {
		Response getRootResponse = createResponse("GET", "/");
		String responseBody = getRootResponse.getBody();

		File publicDirectory = new File("public");
		String[] publicFileNames = publicDirectory.list();

		String listOfDirectoryLinks = HTMLContent.listOfLinks(publicFileNames);
		assertTrue(responseBody.contains(listOfDirectoryLinks));	
	}
}
