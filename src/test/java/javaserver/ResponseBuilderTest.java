package javaserver;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import javaserver.ResponseBuilders.ErrorResponder;
import javaserver.ResponseBuilders.Responder;
import javaserver.Routes;

public class ResponseBuilderTest {

	private String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
	private String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
	private String redirectHeader = "Location: http://localhost:5000/";

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String threeOhTwo = HTTPStatusCode.THREE_OH_TWO.getStatusLine();
	private String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	private String fourOhFive= HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();

	private Response createResponse(String requestLine) {
		Request request = RequestParser.createRequest(requestLine);
		Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(request.getURI()));
		if (responder == null)
			responder = new ErrorResponder();
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

		Response validRequestWithCodedParamResponse = createResponse("GET " + codedURI);
		String responseBody = validRequestWithCodedParamResponse.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}

	@Test
	public void testHTMLHeader() {
		Response getRootResponse = createResponse("GET /");
		assertEquals(getRootResponse.getHeader(), "Content-Type: text/html;");
	}

	@Test
	public void testDirectoryLinks() {
		Response getRootResponse = createResponse("GET /");
		String responseBody = getRootResponse.getBody();

		File publicDirectory = new File("public");
		String[] publicFileNames = publicDirectory.list();

		String listOfDirectoryLinks = HTMLContent.listOfLinks(publicFileNames);
		assertTrue(responseBody.contains(listOfDirectoryLinks));
	}

	@Test
	public void testRespondsToRequestiWithoutURI() {
		Response response = createResponse("GET");
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToBlankRequest() {
		Response response = createResponse("");
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToCrazyRequest() {
		Response response = createResponse("clakjflk aslkgfhaglk");
		assertEquals(response.getResponseCode(), fourOhFour);
	}
}