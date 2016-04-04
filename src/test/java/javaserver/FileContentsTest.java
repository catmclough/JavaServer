package javaserver;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import javaserver.Responders.*;

public class FileContentsTest {
	
	@BeforeClass
	public static void setup() {
		App.initializeDirectoryRouter();
	}

	private Response createResponse(String requestLine) {
		Request request = RequestParser.createRequest(requestLine);
		Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(request.getURI()));
		if (responder == null)
			responder = new ErrorResponder();
		return responder.getResponse(request);
	}

	private Response fileResponse = createResponse("GET /file1");
	private String file1Body = "file1 contents";

	@Test
	public void testFileResponseHasBody() {
		assertFalse(fileResponse.getBody().isEmpty());
	}

	@Test
	public void testGetFileResponseGetsFileContents() {
		assertTrue(fileResponse.getBody().contains(file1Body));
	}
}
