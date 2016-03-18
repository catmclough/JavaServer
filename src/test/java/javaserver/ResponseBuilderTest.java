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

	Request unsuppportedRequest = new Request("GET", "/foo");
	Request requestWithOptions = new Request("GET", "/method_options");
	Request redirectRequest = new Request("GET", "/redirect");
	Request unallowedRequest = new Request("POST", "/file1");
	Request requestWithCodedParams = new Request("GET", codedURI);

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
				Request supportedRequest = new Request(requestMethod, route);
				RequestHandler requestHandler = new RequestHandler(supportedRequest);
				ResponseBuilder responder  = new ResponseBuilder(requestHandler);
				Response response = responder.getResponse();
				assertEquals(response.getResponseCode(), twoHundred);
			}
		}
	}

	@Test
	public void testRespondsWith302() {
		for (String route : Routes.foundRouteRequests.keySet()) {
			for (String requestMethod : Routes.foundRouteRequests.get(route)) {
				Request foundRequest = new Request(requestMethod, route);
				RequestHandler requestHandler = new RequestHandler(foundRequest);
				ResponseBuilder responder  = new ResponseBuilder(requestHandler);
				Response response = responder.getResponse();
				assertEquals(response.getResponseCode(), threeOhTwo);
			}
		}
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		RequestHandler requestHandler = new RequestHandler(unsuppportedRequest);
		ResponseBuilder responder  = new ResponseBuilder(requestHandler);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testMethodNotAllowedResponseCode() throws IOException {
		RequestHandler requestHandler = new RequestHandler(unallowedRequest);
		ResponseBuilder responder  = new ResponseBuilder(requestHandler);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), fourOhFive);
	}

	@Test
	public void testOptionsHeader() throws IOException {
		RequestHandler requestHandler = new RequestHandler(requestWithOptions);
		ResponseBuilder responder  = new ResponseBuilder(requestHandler);
		Response response = responder.getResponse();
		assertEquals(response.getHeader(), methodOptionsHeader);
	}

	@Test
	public void testRedirectHeader() throws IOException {
		RequestHandler requestHandler = new RequestHandler(redirectRequest);
		ResponseBuilder responder  = new ResponseBuilder(requestHandler);
		Response response = responder.getResponse();
		assertEquals(response.getHeader(), redirectHeader);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		RequestHandler requestHandler = new RequestHandler(requestWithCodedParams);
		ResponseBuilder responder  = new ResponseBuilder(requestHandler);
		Response response = responder.getResponse();
		String responseBody = response.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}
}
