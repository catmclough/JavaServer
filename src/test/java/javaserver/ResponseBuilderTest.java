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
	
	@Test
	public void testRespondsWith200() {
		for (String route : Routes.supportedRouteRequests.keySet()) {
			for (String requestMethod : Routes.supportedRouteRequests.get(route)) {
				Response response = getResponse(requestMethod, route);
				assertEquals(response.getResponseCode(), twoHundred);
			}
		}
		
	} 

	@Test
	public void testRespondsWith302() {
		for (String route : Routes.foundRouteRequests.keySet()) {
			for (String requestMethod : Routes.foundRouteRequests.get(route)) {
				Response response = getResponse(requestMethod, route);
				assertEquals(response.getResponseCode(), threeOhTwo);
			}
		}
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		Response unsupportedRequestResponse = getResponse("GET", "/foo");
		assertEquals(unsupportedRequestResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testMethodNotAllowedResponseCode() throws IOException {
		Response unallowedRequestResponse = getResponse("POST", "/file1");
		assertEquals(unallowedRequestResponse.getResponseCode(), fourOhFive);
	}

	@Test
	public void testOptionsHeader() throws IOException {
		Response optionsResponse = getResponse("GET", "/method_options");
		assertEquals(optionsResponse.getHeader(), methodOptionsHeader);
	}

	@Test
	public void testRedirectHeader() throws IOException {
		Response redirectResponse = getResponse("GET", "/redirect");
		assertEquals(redirectResponse.getHeader(), redirectHeader);
	}

	@Test
	public void testValidCodedParameters200() {
		Response validParamsResponse = getResponse("GET", codedURI);
		assertEquals(validParamsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testInvalidCodedParams404() {
		Response invalidParamResponse = getResponse("GET", "/foo?");
		assertEquals(invalidParamResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		Response validRequestWithCodedParamResponse = getResponse("GET", codedURI);
		String responseBody = validRequestWithCodedParamResponse.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}

	private Response getResponse(String requestMethod, String route) {
		Request request = new Request(requestMethod, route);
		RequestHandler requestHandler = new RequestHandler(request);
		ResponseBuilder responder = new ResponseBuilder(requestHandler);
		return responder.getResponse();
	}

}
