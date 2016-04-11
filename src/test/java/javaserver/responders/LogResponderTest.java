package javaserver.responders;

import static org.junit.Assert.*;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestLog;
import javaserver.RequestParser;

public class LogResponderTest {
	private String logsRoute = "/logs";
	private String realm = "Cob Spec Logs";
	private String basicAuth = "WWW-Authenticate: Basic realm=\"" + realm + "\"";
	private RequestLog requestLog = new RequestLog();
	private Responder responder = new LogResponder(new String[] {"GET"}, requestLog);

	private String invalidCredentialsAuthHeader = "Authorization: Basic xxx";
	private String validCredentialsAuthHeader = "Authorization: Basic YWRtaW46aHVudGVyMg==";

	private Request getLogsWithoutAuth = RequestParser.createRequest("GET " + logsRoute);
	private Request getLogsWithInvalidAuth = RequestParser.createRequest("GET " + logsRoute +  System.lineSeparator() + invalidCredentialsAuthHeader);
	private Request getLogsWithValidAuth = RequestParser.createRequest("GET " + logsRoute +  System.lineSeparator() + validCredentialsAuthHeader);

    private String simpleGet = "GET /foo";
	private String fourOhOne = HTTPStatusCode.FOUR_OH_ONE.getStatusLine();

	@Test
	public void testLogResponderCreation() {
		assertEquals(responder.getClass(), LogResponder.class);
	}

	@Test
	public void testGetLogsNoAuthHeaderResponseCode() {
		assertEquals(responder.getResponse(getLogsWithoutAuth).getResponseCode(), fourOhOne);
	}

	@Test
	public void testGetLogsWithInvalidAuthResponseCode() {
	   assertEquals(responder.getResponse(getLogsWithInvalidAuth).getResponseCode(), fourOhOne);
	}

	@Test
	public void testGetLogsWithValidAuthResponseCode() {
	   assertEquals(responder.getResponse(getLogsWithValidAuth).getResponseCode(), HTTPStatusCode.TWO_HUNDRED.getStatusLine());
	}

	@Test
	public void testGetLogsWithInvalidAuthHeader() {
	   assertEquals(responder.getResponse(getLogsWithInvalidAuth).getHeader(), basicAuth);
	}

	@Test
	public void testGetLogsWithoutAuthResponseHeader() {
		assertEquals(responder.getResponse(getLogsWithoutAuth).getHeader(), basicAuth);
	}

	@Test
	public void testGetLogsWithValidAuthBody() {
	   requestLog.addRequest(simpleGet);
	   assertTrue(responder.getResponse(getLogsWithValidAuth).getBody().contains(simpleGet));
	}

	@Test
	public void testGetLogsBodyWithoutAuthBody() {
	    requestLog.addRequest(simpleGet);
	    assertFalse(responder.getResponse(getLogsWithoutAuth).getBody().contains(simpleGet));
	}
}
