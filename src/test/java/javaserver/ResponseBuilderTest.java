package javaserver;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ResponseBuilderTest {
	ResponseBuilder testResponseBuilder;
	SocketWriter writer;
	ResponseBuilder responder;
	RequestParser requestParser;

	String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	private String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
	private String redirectHeader = "Location: http://localhost:5000/";

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String threeOhTwo = HTTPStatusCode.THREE_OH_TWO.getStatusLine();
	private String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	private String fourOhFive= HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();

	private Response createResponse(String requestLine) {
		Request request = RequestParser.createRequest(requestLine);
		ResponseBuilder responder = Routes.routeResponders.get(RequestParser.getURIWithoutParams(request.getURI()));
		if (responder == null)
			responder = new ErrorResponseBuilder();
		return responder.getResponse(request);
	}

	@Test
	public void testRespondsWith200() {
		Response response = createResponse("GET /");
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testRedirectRespondsWith302() {
		Response response = createResponse("GET /redirect");
		assertEquals(response.getResponseCode(), threeOhTwo);
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		Response unsupportedRequestResponse = createResponse("GET /foo");
		assertEquals(unsupportedRequestResponse.getResponseCode(), fourOhFour);
	}
		
	@Test
	public void testAcceptableFileRequest() throws IOException {
		Response response = createResponse("GET /file1");
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testThreeOhTwoResponseCode() throws IOException {
		Response response = createResponse("GET /redirect");
		assertEquals(response.getResponseCode(), threeOhTwo);
	}
	
	public void testSimplePost() {
		Response postFormResponse = createResponse("POST /form");
		assertEquals(postFormResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testSimplePut() {
		Response postFormResponse = createResponse("PUT /form");
		assertEquals(postFormResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testMethodNotAllowedResponseCode() throws IOException {
		Response unallowedRequestResponse = createResponse("POST /file1");
		assertEquals(unallowedRequestResponse.getResponseCode(), fourOhFive);
	}

	@Test
	public void testOptionsResponseCode() throws IOException {
		Response optionsResponse = createResponse("GET /method_options");
		assertEquals(optionsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testOptionsHeader() throws IOException {
		Response optionsResponse = createResponse("GET /method_options");
		assertEquals(optionsResponse.getHeader(), methodOptionsHeader);
	}

	@Test
	public void testRedirectHeader() throws IOException {
		Response redirectResponse = createResponse("GET /redirect");
		assertEquals(redirectResponse.getHeader(), redirectHeader);
	}

	@Test
	public void testValidCodedParameters200() {
		Response validParamsResponse = createResponse("GET " + codedURI);
		assertEquals(validParamsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testInvalidCodedParams404() {
		Response invalidParamResponse = createResponse("GET /foo?");
		assertEquals(invalidParamResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		ResponseBuilder responder = new ResponseBuilder(requestWithCodedParams);
		Response response = responder.getResponse();
		String responseBody = response.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}

	@Test
	public void testCodedParamsGets200Response() {
		ResponseBuilder responder = new ResponseBuilder(requestWithCodedParams);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}
}
