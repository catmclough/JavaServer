package javaserver;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class ResponseBuilderTest {
	ResponseBuilder testResponseBuilder;
	SocketWriter writer;
	ResponseBuilder responder;
	RequestParser requestParser;

	String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	Request getRoot = new Request("GET", "/");
	Request getForm = new Request("GET", "/form");
	Request postForm = new Request("POST", "/form");
	Request putForm = new Request("PUT", "/form");
	Request getBogusRoute = new Request("GET", "/foo");
	Request getMethodOptions = new Request("GET", "/method_options");
	Request getCodedParams = new Request("GET", codedURI);

	String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	String fourOhFour= HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();


	@Before
	public void setUp() {
		App.configureRoutes();
	}

	@Test
	public void testCreatesReponseWithResponseCode() {
		ResponseBuilder responder = new ResponseBuilder(getRoot);
		Response response = responder.getResponse();
		assertNotNull(response.getResponseCode());
	}

	@Test
	public void testCreatesReponseWithHeader() {
		ResponseBuilder responder = new ResponseBuilder(getMethodOptions);
		Response response = responder.getResponse();
		assertNotNull(response.getHeader());
	}

	@Test
	public void testCreatesReponseWithBody() {
		ResponseBuilder responder = new ResponseBuilder(getCodedParams);
		Response response = responder.getResponse();
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetRootResponseCode() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(getRoot);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testGetFormResponseCode() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(getForm);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPostFormResponseCode() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(postForm);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPutFormResponseCode() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(putForm);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(getBogusRoute);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), fourOhFour);
	}

	@Test
	public void testMethodOptionsResponseCode() {
		ResponseBuilder responder = new ResponseBuilder(getMethodOptions);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testMethodOptionsHeader() throws IOException {
		ResponseBuilder responder = new ResponseBuilder(getMethodOptions);
		Response response = responder.getResponse();
		String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
		assertEquals(response.getHeader(), methodOptionsHeader);
	}

	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";

		ResponseBuilder responder = new ResponseBuilder(getCodedParams);
		Response response = responder.getResponse();
		String responseBody = response.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}

	@Test
	public void testCodedParamsGets200Response() {
		ResponseBuilder responder = new ResponseBuilder(getCodedParams);
		Response response = responder.getResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}
}
