package javaserver;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class ResponseBuilderTest {
	ResponseBuilder testResponseBuilder;
	SocketWriter writer;
	Responder responder;
	
	String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";

	Request getRoot = new Request("GET", "/");
	Request getForm = new Request("GET", "/form");
	Request postForm = new Request("POST", "/form");
	Request putForm = new Request("PUT", "/form");
	Request getBogusRoute = new Request("GET", "/foo");
	Request getMethodOptions = new Request("GET", "/method_options");
	Request getCodedParams = new Request("GET", codedURI);
	
	String twoHundred = HTTPStatusCodes.TWO_HUNDRED;
	String fourOhFour = HTTPStatusCodes.FOUR_OH_FOUR;


	@Before
	public void setUp() {
		App.configureRoutes();
	}

	@Test
	public void testCreatesReponseWithResponseCode() {
		ResponseBuilder responseBuilder = new ResponseBuilder(getRoot);
		assertNotNull(responseBuilder.createResponse().getResponseCode());
	}

	@Test
	public void testCreatesReponseWithHeader() {
		ResponseBuilder responseBuilder = new ResponseBuilder(getMethodOptions);
		assertNotNull(responseBuilder.createResponse().getHeader());
	}

	@Test
	public void testCreatesReponseWithBody() {
		ResponseBuilder responseBuilder = new ResponseBuilder(getCodedParams);
		assertNotNull(responseBuilder.createResponse().getBody());
	}

	@Test
	public void testGetRootResponseCode() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(getRoot);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testGetFormResponseCode() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(getForm);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPostFormResponseCode() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(postForm);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testPutFormResponseCode() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(putForm);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

	@Test
	public void testFourOhFourResponseCode() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(getBogusRoute);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), fourOhFour);
	}
	
	@Test
	public void testMethodOptionsResponseCode() {
		ResponseBuilder responseBuilder = new ResponseBuilder(getMethodOptions);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}
	
	@Test
	public void testMethodOptionsHeader() throws IOException {
		ResponseBuilder responseBuilder = new ResponseBuilder(getMethodOptions);
		Response response = responseBuilder.createResponse();
		String methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT";
		assertEquals(response.getHeader(), methodOptionsHeader);
	}
	
	@Test
	public void testDecodedParamsInResponseBody() {
		String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
		String decodedParamTwo = "variable_2 = stuff";
		
		ResponseBuilder responseBuilder = new ResponseBuilder(getCodedParams);
		Response response = responseBuilder.createResponse();
		String responseBody = response.getBody();

		assertTrue(responseBody.contains(decodedParamOne));
		assertTrue(responseBody.contains(decodedParamTwo));
	}
	
	@Test
	public void testCodedParamsGets200Response() {
		ResponseBuilder responseBuilder = new ResponseBuilder(getCodedParams);
		Response response = responseBuilder.createResponse();
		assertEquals(response.getResponseCode(), twoHundred);
	}

}
