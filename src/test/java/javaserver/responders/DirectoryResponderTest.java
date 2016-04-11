package javaserver.responders;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import javaserver.Request;
import javaserver.HTMLContent;
import javaserver.HTTPStatusCode;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class DirectoryResponderTest {
	private String directoryRoute = "/";
	private Request supportedRequest = RequestParser.createRequest("GET " + directoryRoute);
	private String directoryContentType = "Content-Type: text/html;";

	private Responder responder = Routes.getResponder(directoryRoute);

	private String[] getListOfPublicFiles() {
		File publicDirectory = new File("public");
		return publicDirectory.list();
	}

	@Test
	public void testDirectoryResponderCreation() {
	    assertEquals(responder.getClass(), DirectoryResponder.class);
	}

	@Test
	public void testRespondsWith200() {
		Response response = responder.getResponse(supportedRequest);
		assertEquals(response.getResponseCode(), HTTPStatusCode.TWO_HUNDRED.getStatusLine());
	}

	@Test
	public void testRespondsWith404() {
	    Request unsupportedRequest = RequestParser.createRequest("POST " + directoryRoute);
		Response unsupportedRequestResponse = responder.getResponse(unsupportedRequest);
		assertEquals(unsupportedRequestResponse.getResponseCode(), HTTPStatusCode.FOUR_OH_FOUR.getStatusLine());
	}

	@Test
	public void testRespondsWithContentTypeHeader() {
		Response response = responder.getResponse(supportedRequest);
		assertEquals(response.getHeader(), directoryContentType);
	}

	@Test
	public void testDirectoryLinksBody() {
		Response response = responder.getResponse(supportedRequest);
		String responseBody = response.getBody();

		String listOfDirectoryLinks = HTMLContent.listOfLinks(getListOfPublicFiles());
		assertTrue(responseBody.contains(listOfDirectoryLinks));
	}
}
