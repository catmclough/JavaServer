package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestLog;
import javaserver.RequestParser;

public class LogResponderTest {
	String logsRoute = "/logs";
	RequestLog requestLog = new RequestLog();
	Responder responder = new LogResponder(new String[] {"GET"}, requestLog);

	String invalidUserName = "foo";
	String invalidPassword = "bar";
	String invalidCredentialsAuthHeader = "Authorization: Basic xxx";
	String validCredentialsAuthHeader = "Authorization: Basic YWRtaW46aHVudGVyMg==";

	Request getLogsWithoutAuth = RequestParser.createRequest("GET " + logsRoute); 
	Request getLogsWithInvalidAuth = RequestParser.createRequest("GET " + logsRoute +  System.lineSeparator() + invalidCredentialsAuthHeader);
	Request getLogsWithValidAuth = RequestParser.createRequest("GET " + logsRoute +  System.lineSeparator() + validCredentialsAuthHeader);

	String realm = "Cob Spec Logs";
	String basicAuth = "WWW-Authenticate: Basic realm=\"" + realm + "\"";

	String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	String fourOhOne = HTTPStatusCode.FOUR_OH_ONE.getStatusLine();

	@Test
	public void testLogResponderCreation() {
		assertEquals(responder.getClass(), LogResponder.class);
	}
	
	@Test
	public void testGetLogsNoAuthHeaderResponseCode() {
		assertEquals(responder.getResponse(getLogsWithoutAuth).getResponseCode(), fourOhOne);
	}

	@Test
	public void testGetLogsWithoutAuthResponseHeader() {
		assertEquals(responder.getResponse(getLogsWithoutAuth).getHeader(), basicAuth);
	}
	
	@Test
	public void testGetLogsWithInvalidAuthResponseCode() {
	   assertEquals(responder.getResponse(getLogsWithInvalidAuth).getResponseCode(), fourOhOne); 
	}

	@Test
	public void testGetLogsWithInvalidAuthHeader() {
	   assertEquals(responder.getResponse(getLogsWithInvalidAuth).getHeader(), basicAuth); 
	}

	@Test
	public void testGetLogsWithValidAuthResponseCode() {
	   assertEquals(responder.getResponse(getLogsWithValidAuth).getResponseCode(), twoHundred); 
	}
	
	@Test
	public void testGetLogsWithValidAuthBody() {
	   String simpleGet = "GET /foo";
	   requestLog.addRequest(simpleGet);
	   assertTrue(responder.getResponse(getLogsWithValidAuth).getBody().contains(simpleGet));
	}
}
