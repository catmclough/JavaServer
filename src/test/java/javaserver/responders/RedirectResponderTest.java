package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class RedirectResponderTest {
	private String redirectRoute = "/redirect";
	private String redirectHeader = "Location: http://localhost:5000/";
	private Request supportedRequest = RequestParser.createRequest("GET " + redirectRoute);
	private Request unsupportedRequest = RequestParser.createRequest("POST " + redirectRoute);

	private Responder responder = Routes.getResponder(redirectRoute);
	private String threeOhTwo = HTTPStatusCode.THREE_OH_TWO.getStatusLine();
	private String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();

	@Test
	public void testRedirectRespondsWith302() {
		Response response = responder.getResponse(supportedRequest);
		assertEquals(response.getResponseCode(), threeOhTwo);
	}

	@Test
	public void testInvalidRedirectRespondsWith404() {
		Response response = responder.getResponse(unsupportedRequest);
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testRedirectHeader() {
		Response redirectResponse = responder.getResponse(supportedRequest);
		assertEquals(redirectResponse.getHeader(), redirectHeader);
	}
}
