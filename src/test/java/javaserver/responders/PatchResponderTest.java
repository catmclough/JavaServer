package javaserver.responders;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class PatchResponderTest {

	private String patchRequest(String ifMatchHeader) {
	    return "PATCH " + fileRoute + System.lineSeparator() + ifMatchHeader 
	        + System.lineSeparator() + System.lineSeparator() + patchedContent;
	}

	private Response getFile() {
	    Responder fileResponder = Routes.getResponder(fileRoute);
	    Request request = RequestParser.createRequest("GET " + fileRoute);
	    Response fileResponse = fileResponder.getResponse(request);
	    return fileResponse;
	}

	private String publicDirectory = "public/";
	private String fileRoute = "/patch-content.txt";
	private String defaultContentMatchHeader = "If-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
	private String nonMatchingEtagHeader = "If-Match: x";

	private String patchedContent = "new content";
	private String defaultFileContents = "default content";

	private String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	private String twoOhFour = HTTPStatusCode.TWO_OH_FOUR.getStatusLine();

	String[] supportedMethods = new String[] {"GET", "PATCH"};
	File publicDir = new File("public/");
	private PatchResponder responder = new PatchResponder(supportedMethods, publicDir);
	
	private Request validPatchRequest = RequestParser.createRequest(patchRequest(defaultContentMatchHeader));

	@After
	public void resetFileToDefault() throws IOException {
	   File file = new File(publicDirectory + fileRoute); 
	   Files.write(file.toPath(), defaultFileContents.getBytes());
	}
	

	@Test
	public void testGetResponse() {
	   assertEquals(getFile().getResponseCode(), twoHundred); 
	   assertEquals(getFile().getBody(), defaultFileContents); 
	}

	@Test
	public void testNonMatchingEtagDoesNotChangeFileContents() {
	    Request invalidPatchRequest = RequestParser.createRequest(patchRequest(nonMatchingEtagHeader));
	    responder.getResponse(invalidPatchRequest);
	    assertEquals(getFile().getBody(), defaultFileContents);
	}
	
	@Test
	public void testPatchResponseCode() {
	    Response validPatchResponse = responder.getResponse(validPatchRequest);
	    assertEquals(validPatchResponse.getResponseCode(), twoOhFour);
	}
	
	@Test
	public void TestRecognizesEtagMatch() {
	   assertTrue(responder.etagMatchesFileContent(validPatchRequest)); 
	}

	@Test
	public void writesDataWhenEtagMatches() {
	    assertEquals(getFile().getBody(), defaultFileContents);
	    responder.getResponse(validPatchRequest);
	    assertEquals(getFile().getBody(), patchedContent);
	}
}
