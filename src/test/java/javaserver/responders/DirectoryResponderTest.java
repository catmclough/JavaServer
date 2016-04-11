package javaserver.responders;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

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
	private Request unsupportedRequest = RequestParser.createRequest("POST " + directoryRoute);

	private Responder responder = Routes.getResponder(directoryRoute);

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
	private String contentType = "Content-Type: text/html;";

	@Test
	public void testRespondsWith200() {
		Response response = responder.getResponse(supportedRequest);
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testRespondsWithFourOhFour() {
		Response unsupportedRequestResponse = responder.getResponse(unsupportedRequest);
		assertEquals(unsupportedRequestResponse.getResponseCode(), fourOhFour);
	}
	
	@Test
	public void testRespondsWithContentTypeHeader() {
		Response response = responder.getResponse(supportedRequest);
		assertEquals(response.getHeader(), contentType);
	}

//	@Test
//	public void testDirectoryLinksBody() {
//		Response response = responder.getResponse(supportedRequest);
//		byte[] responseBody = response.getBody();
//
//		File publicDirectory = new File("public");
//		String[] publicFileNames = publicDirectory.list();
//
//		String listOfDirectoryLinks = HTMLContent.listOfLinks(publicFileNames);
//		assertTrue(Arrays.asList(responseBody).contains(listOfDirectoryLinks));
//	}
//
}
