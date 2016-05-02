package responders;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import http_messages.HTTPStatus;
import http_messages.Request;
import javaserver.RequestLog;
import responders.LogResponder;
import responders.Responder;

public class LogResponderTest {
    private String logsRoute = "/logs";
    private String realm = "Cob Spec Logs";
    private String basicAuth = "WWW-Authenticate: Basic realm=\"" + realm + "\"";
    private RequestLog requestLog = RequestLog.getInstance();
    private Responder responder = new LogResponder(new String[] {"GET"});

    private String invalidCredentialsAuthHeader = "Authorization: Basic xxx";
    private String validCredentialsAuthHeader = "Authorization: Basic YWRtaW46aHVudGVyMg==";

    private Request getLogsWithoutAuth = new Request.RequestBuilder("GET " + logsRoute).build();
    private Request getLogsWithInvalidAuth = new Request.RequestBuilder("GET " + logsRoute +  Request.newLine + invalidCredentialsAuthHeader).build();
    private Request getLogsWithValidAuth = new Request.RequestBuilder("GET " + logsRoute +  Request.newLine + validCredentialsAuthHeader).build();

    private String simpleGet = "GET /foo";
    private String fourOhOne = HTTPStatus.UNAUTHORIZED.getStatusLine();

    @Before
    public void clearLog() {
        requestLog.clearLog();
    }

    @Test
    public void testLogResponderCreation() {
        assertEquals(responder.getClass(), LogResponder.class);
    }

    @Test
    public void testGetLogsNoAuthHeaderResponseCode() {
        assertEquals(responder.getResponse(getLogsWithoutAuth).getStatusLine(), fourOhOne);
    }

    @Test
    public void testGetLogsWithInvalidAuthResponseCode() {
        assertEquals(responder.getResponse(getLogsWithInvalidAuth).getStatusLine(), fourOhOne);
    }

    @Test
    public void testGetLogsWithValidAuthResponseCode() {
        assertEquals(responder.getResponse(getLogsWithValidAuth).getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testGetLogsWithInvalidAuthHeader() {
        assertEquals(responder.getResponse(getLogsWithInvalidAuth).getHeaders()[0].getLine(), basicAuth);
    }

    @Test
    public void testGetLogsWithoutAuthResponseHeader() {
        assertEquals(responder.getResponse(getLogsWithoutAuth).getHeaders()[0].getLine(), basicAuth);
    }

    @Test
    public void testGetLogsWithValidAuthBody() {
        requestLog.addRequest(simpleGet);
        assertEquals(responder.getResponse(getLogsWithValidAuth).getBody(), simpleGet);
    }

    @Test
    public void testGetLogsBodyWithoutAuthBody() {
        requestLog.addRequest(simpleGet);
        assertFalse(responder.getResponse(getLogsWithoutAuth).getBody().contains(simpleGet));
    }
}
