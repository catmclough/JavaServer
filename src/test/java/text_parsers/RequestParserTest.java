package text_parsers;

import static org.junit.Assert.*;
import org.junit.Test;
import text_parsers.RequestParser;

public class RequestParserTest {
    private String rawRequest = "POST /users/123 HTTP/1.1";
    private String requestMethod = "POST";
    private String requestURI = "/users/123";
    private String partialDataHeader = "Range: bytes=0-4";
    private String requestWithRange = "GET /partial_content.txt HTTP/1.1" + System.lineSeparator() + partialDataHeader;
    private String unimportantHeader = "Content-Length: 15";
    private String etagHeader = "If-Match: xyz";
    private String patchData = "new content";
    private String requestWithHeadersAndData = "PATCH /path-content.txt HTTP/1.1" + System.lineSeparator() + unimportantHeader  + System.lineSeparator() + etagHeader +
            System.lineSeparator() + System.lineSeparator() + patchData;

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
        assertEquals(RequestParser.getKnownRawRequestHeaders(requestWithRange)[0], partialDataHeader);
    }

    @Test
    public void testParsesKnownHeaders() {
        assertEquals(RequestParser.getKnownRawRequestHeaders(requestWithHeadersAndData)[1], etagHeader);
    }

    @Test
    public void testParsesData() {
        assertEquals(RequestParser.getRequestData(requestWithHeadersAndData), patchData);
    }
}
