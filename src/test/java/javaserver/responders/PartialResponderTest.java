package javaserver.responders;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.Test;
import javaserver.App;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;


public class PartialResponderTest {
	String fileRoute = "/partial_content.txt";
	String unknownFileRoute = "/unknown-file.txt";

	Request fullRangeRequest = RequestParser.createRequest("GET " + fileRoute + "\nRange: bytes=0-4");
	Request noBeginningPartial = RequestParser.createRequest("GET " + fileRoute + "\nRange: bytes=-4");
	Request noEndPointPartial = RequestParser.createRequest("GET " + fileRoute + "\nRange: bytes=4-");

	Responder responder = new PartialResponder(new String[] {"GET"}, new File(App.DEFAULT_PUBLIC_DIRECTORY));

	private String twoOhSix = HTTPStatusCode.TWO_OH_SIX.getStatusLine();

	@Test
	public void testFullRangePartialRequestResponseCode() {
		Response partial = responder.getResponse(fullRangeRequest);
		assertEquals(partial.getResponseCode(), twoOhSix);
	}

	@Test
	public void testHalfOfRangeRequestResponseCode() {
		Response noBeginningPartialResponse = responder.getResponse(noBeginningPartial);
		assertEquals(noBeginningPartialResponse.getResponseCode(), twoOhSix);

		Response noEndPartialResponse = responder.getResponse(noEndPointPartial);
		assertEquals(noEndPartialResponse.getResponseCode(), twoOhSix);
	}
}
