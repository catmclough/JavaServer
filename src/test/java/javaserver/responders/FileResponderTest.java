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
	Request getPartial = RequestParser.createRequest("GET " + fileRoute + "\nRange: bytes=0-4");
	Request patchContent = RequestParser.createRequest("PATCH " + fileRoute + "\nIf-Match: xyz");
	Responder responder = Routes.getResponder(fileRoute);

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String twoOhFour = HTTPStatusCode.TWO_OH_FOUR.getStatusLine();
	private String twoOhSix = HTTPStatusCode.TWO_OH_SIX.getStatusLine();
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

	@Test
	public void testCreatesAndRespondsWithPartialResponder() {
	  Response partialResponse = responder.getResponse(getPartial);
	  assertEquals(partialResponse.getResponseCode(), twoOhSix);
	}

	@Test
	public void testCreatesAndRespondsWithPatchResponder() {
	  Response patchResponse = responder.getResponse(patchContent);
	  assertEquals(patchResponse.getResponseCode(), twoOhFour);
	}
}
