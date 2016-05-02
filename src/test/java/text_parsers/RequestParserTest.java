package text_parsers;

import static org.junit.Assert.*;
import org.junit.Test;

import http_messages.Request;
import text_parsers.RequestParser;

public class RequestParserTest {
    private String rawRequest = "POST /users/123 HTTP/1.1";
    private String requestMethod = "POST";
    private String requestURI = "/users/123";
    private String partialDataHeader = "Range: bytes=0-4";
    private String requestWithRange = "GET /partial_content.txt HTTP/1.1" + Request.newLine + partialDataHeader;
    private String unimportantHeader = "Content-Length: 15";
    private String etagHeader = "If-Match: xyz";
    private String patchData = "new content";
    private String requestWithHeadersAndData = "PATCH /path-content.txt HTTP/1.1" + Request.newLine + unimportantHeader  + Request.newLine + etagHeader +
            Request.newLine + Request.newLine + patchData;

    @Test
    public void testParsesMethod() {
        assertEquals(RequestParser.getRequestMethod(rawRequest), requestMethod);
    }

    @Test
    public void testParsesURI() {
        assertEquals(RequestParser.getRequestURI(rawRequest), requestURI);
    }

    @Test
    public void testParsesRawRangeHeader() {
        assertEquals(RequestParser.getRawRequestHeaders(requestWithRange)[0], partialDataHeader);
    }

    @Test
    public void testParsesHeaders() {
        assertEquals(RequestParser.getRawRequestHeaders(requestWithHeadersAndData)[1], etagHeader);
    }

    @Test
    public void testParsesData() {
        assertEquals(RequestParser.getRequestData(requestWithHeadersAndData), patchData);
    }
}
