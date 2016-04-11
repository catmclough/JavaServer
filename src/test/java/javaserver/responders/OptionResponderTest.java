package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class OptionResponderTest {
	private String optionRoute = "/method_options";
	private String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
	private Request methodOptionsRequest = RequestParser.createRequest("GET " + optionRoute);
	private Responder responder = Routes.getResponder(optionRoute);
	private Response optionsResponse = responder.getResponse(methodOptionsRequest);
	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();

	@Test
	public void testOptionsResponseCode() {
		assertEquals(optionsResponse.getResponseCode(), twoHundred);
	}

	@Test
	public void testOptionsHeader() {
		assertEquals(optionsResponse.getHeader(), methodOptionsHeader);
	}
}
