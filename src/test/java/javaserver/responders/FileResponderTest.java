package javaserver.responders;

import static org.junit.Assert.*;

import org.junit.Test;

import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class FileResponderTest {

	String fileRoute = "/file1";
	String fileContents = "file1 contents";

	Request getFile = RequestParser.createRequest("GET " + fileRoute);
	Request postFile = RequestParser.createRequest("POST " + fileRoute);
	Responder responder = Routes.getResponder(fileRoute);

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String fourOhFive= HTTPStatusCode.FOUR_OH_FIVE.getStatusLine();

	@Test
	public void testSupportedFileRequestResponseCode() {
		Response fileResponse = responder.getResponse(getFile);
		assertEquals(fileResponse.getResponseCode(), twoHundred);
	}
	
	@Test
	public void testMethodNotAllowedResponseCode() {
		Response unallowedRequestResponse = responder.getResponse(postFile);
		assertEquals(unallowedRequestResponse.getResponseCode(), fourOhFive);
	}

	@Test
	public void testFileContentsInBody() {
		Response existingFileResponse = responder.getResponse(getFile);
		assertTrue(existingFileResponse.getBody().contains(fileContents));
	}
}
