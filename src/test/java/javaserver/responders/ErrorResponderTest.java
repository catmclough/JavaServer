package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class ErrorResponderTest {
	private String unknownRoute = "/foobar";
	private Responder responder = Routes.getResponder(unknownRoute);

	private String unknownRouteWithParams = "/foobar?var1=xyz";
	private String unknownFileWithPartial = "/foobar\nRange: bytes=0-10";
	private Request unsupportedRequest = RequestParser.createRequest("GET " + unknownRoute);

	private String fourOhFour = HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();

	@Test
	public void testErrorResponderCreation() {
	    assertEquals(responder.getClass(), ErrorResponder.class);
	}

	@Test
	public void test404ResponseCode() {
		Response unsupportedRequestResponse = responder.getResponse(unsupportedRequest);
		assertEquals(unsupportedRequestResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToRequestiWithoutURI() {
		Response response = responder.getResponse(RequestParser.createRequest("GET"));
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToBlankRequest() {
		Response response = responder.getResponse(RequestParser.createRequest(""));
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToCrazyRequest() {
		Response response = responder.getResponse(RequestParser.createRequest(""));
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRespondsToInvalidCodedParams() {
		Responder responder = Routes.getResponder(unknownRouteWithParams);
		assertEquals(responder.getClass(), ErrorResponder.class);

		Response unknownRouteResponse = responder.getResponse(RequestParser.createRequest(unknownRouteWithParams));
		assertEquals(unknownRouteResponse.getResponseCode(), fourOhFour);
	}

	@Test
	public void testInvalidiFileRequestWithPartial() {
		Response invalidRangeResponse = responder.getResponse(RequestParser.createRequest(unknownFileWithPartial));
		assertEquals(invalidRangeResponse.getResponseCode(), fourOhFour);
	}
}

