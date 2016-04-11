package javaserver;

import static org.junit.Assert.*;
import org.junit.Test;

import javaserver.responders.ErrorResponder;
import javaserver.responders.Responder;

public class PartialResponseTest {

	String partialRequestWithFullRange = "GET /partial_content.txt HTTP/1.1\nRange: bytes=0-4";
	String partialRequestHeader = "Content-Length: 5";

	private Response createResponse(String requestLine) {
		Request request = RequestParser.createRequest(requestLine);
		Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(request.getURI()));
		if (responder == null)
			responder = new ErrorResponder();
		return responder.getResponse(request);
	}

//	@Test
//	public void testPartialContentWithFullRange() {
//		String firstFiveBytes = "This ";
//		Response partialResponse = createResponse(partialRequestWithFullRange);
//		assertEquals(partialResponse.getBody(), firstFiveBytes);
//	}
//
//	@Test
//	public void testPartialContentWithEndOfRange() {
//		String partialRequestWithEndOfRange = "GET /partial_content.txt HTTP/1.1\nRange: bytes=-6";
//		String lastSixBytes = " 206.\n";
//		Response partialResponse = createResponse(partialRequestWithEndOfRange);
//		assertEquals(partialResponse.getBody(), lastSixBytes);
//	}
//
//	@Test
//	public void testPartialContentWithStartOfRange() {
//		String fileRequestWithStartOfRange = "GET /partial_content.txt HTTP/1.1\nRange: bytes=4-";
//		String requestedBytes = " is a file that contains text to read part of in order to fulfill a 206.\n";
//		Response partialResponse = createResponse(fileRequestWithStartOfRange);
//		assertEquals(partialResponse.getBody(), requestedBytes);
//	}
}
