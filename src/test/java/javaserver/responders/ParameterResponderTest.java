package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class ParameterResponderTest {

	private String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
	private Request requestWithParams = RequestParser.createRequest("GET " + codedURI);
	private Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(codedURI));

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();

	@Test
	public void testValidCodedParameters200() {
		Response validParamsResponse = responder.getResponse(requestWithParams);
		assertEquals(validParamsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		Response validRequestWithCodedParamResponse = responder.getResponse(requestWithParams);
		String responseBody = validRequestWithCodedParamResponse.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}
}
