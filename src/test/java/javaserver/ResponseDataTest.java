package javaserver;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import javaserver.Responders.*;

public class ResponseDataTest {

	private String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
	private String redirectHeader = "Location: http://localhost:5000/";
	private String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	private Response createResponse(String requestLine) {
		Request request = RequestParser.createRequest(requestLine);
		Responder responder = Routes.getResponder(RequestParser.getURIWithoutParams(request.getURI()));
		if (responder == null)
			responder = new ErrorResponder();
		return responder.getResponse(request);
	}

	@Test
	public void testOptionsHeader() throws IOException {
		Response optionsResponse = createResponse("GET /method_options");
		assertEquals(optionsResponse.getHeader(), methodOptionsHeader);
	}


	@Test
	public void testRedirectHeader() throws IOException {
		Response redirectResponse = createResponse("GET /redirect");
		assertEquals(redirectResponse.getHeader(), redirectHeader);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		Response validRequestWithCodedParamResponse = createResponse("GET " + codedURI);
		String responseBody = validRequestWithCodedParamResponse.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}

	@Test
	public void testHTMLHeader() {
		Response getRootResponse = createResponse("GET /");
		assertEquals(getRootResponse.getHeader(), "Content-Type: text/html;");
	}

	@Test
	public void testDirectoryLinks() {
		Response getRootResponse = createResponse("GET /");
		String responseBody = getRootResponse.getBody();

		File publicDirectory = new File("public");
		String[] publicFileNames = publicDirectory.list();

		String listOfDirectoryLinks = HTMLContent.listOfLinks(publicFileNames);
		assertTrue(responseBody.contains(listOfDirectoryLinks));
	}
	
	@Test
	public void testFormInitializedWithEmptyDataLine() {
		Response getForm = createResponse("GET /form");
		assertTrue(getForm.getBody().isEmpty());
	}

	@Test
	public void testPostDataToForm() {
		createResponse("POST /form\n\nsnack=crackerjack");
		Response getUpdatedForm = createResponse("GET /form");
		assertTrue(getUpdatedForm.getBody().contains("snack=crackerjack"));
	}

	@Test
	public void testDeleteForm() {
		createResponse("DELETE /form");
		Response getNewForm = createResponse("GET /form");
		assertTrue(getNewForm.getBody().isEmpty());
	}
}
