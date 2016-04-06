package javaserver;

import static org.junit.Assert.*;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import javaserver.Routes;
import javaserver.Responders.ErrorResponder;
import javaserver.Responders.Responder;

public class ResponseCodesTest {

	private String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String twoOhSix = HTTPStatusCode.TWO_OH_SIX.getStatusLine();
	private String threeOhTwo = HTTPStatusCode.THREE_OH_TWO.getStatusLine();
	private String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	private String fourOhFive= HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();

	@BeforeClass
	public static void setup() {
		App.initializeDirectoryRouter();
	}

	private Response createResponse(String fullRequest) {
		Request request = RequestParser.createRequest(fullRequest);
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
	public void testGetForm() {
		String postFormResponseCode = createResponse("GET /form").getResponseCode();
		assertEquals(postFormResponseCode, twoHundred);
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

	@Test
	public void testResponseToPostWithData() {
		Response postForm = createResponse("POST /form\n\nsnack=crackerjack");
		assertEquals(postForm.getResponseCode(), twoHundred);
	}

	@Test
	public void testResponseToDeleteForm() {
		Response postForm = createResponse("DELETE /form");
		assertEquals(postForm.getResponseCode(), twoHundred);
	}

	@Test
	public void testPartialWithFullRange() {
		Response partial = createResponse("GET /partial_content.txt\nRange: bytes=0-4");
		assertEquals(partial.getResponseCode(), twoOhSix);
	}

	@Test
	public void testPartialsWithHalfOfRange() {
		Response partialWithEndRange = createResponse("GET /partial_content.txt\nRange: bytes=-4");
		assertEquals(partialWithEndRange.getResponseCode(), twoOhSix);

		Response partialWithStartRange = createResponse("GET /partial_content.txt\nRange: bytes=-4");
		assertEquals(partialWithStartRange.getResponseCode(), twoOhSix);
	}

	@Test
	public void testInvalidPartial() {
		Response invalidPartial = createResponse("GET /non-existant.txt\nRange: bytes=0-3");
		assertEquals(invalidPartial.getResponseCode(), fourOhFour);
	}
}
