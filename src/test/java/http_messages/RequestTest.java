package http_messages;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class RequestTest {
    private Header[] headers = new Header[] {new Header.HeaderBuilder("Content-Length: 15").build(),
                                             new Header.HeaderBuilder("Host: localhost:5000").build(),
                                             new Header.HeaderBuilder("Range: bytes=0-2").build()};
    private String headerLines = headers[0].getLine() + Request.newLine +
                                 headers[1].getLine() + Request.newLine +
                                 headers[2].getLine() + Request.newLine + Request.newLine;
    private String data = "some patched content";
    private String complicatedRawRequest = "PATCH /some%20file.txt?var_1=wat%20the&var_2=foo" + Request.newLine + headerLines + data;

    private String[] decodedParams = new String[] {"var_1 = wat the", "var_2 = foo"};
    private Request complicatedRequest = new Request.RequestBuilder(complicatedRawRequest).build();

    private Request simpleGet = new Request.RequestBuilder("GET /").build();

    @Test
    public void requestHasMethod() {
        assertEquals("PATCH", complicatedRequest.getMethod());
    }

    @Test
    public void uriIsDecodedAndKeepsParams() {
        assertEquals("/some file.txt?var_1=wat the&var_2=foo", complicatedRequest.getURI());
    }

    @Test
    public void paramsAreSeparatedAndDecoded() {
        assertTrue(Arrays.equals(complicatedRequest.getParams(), decodedParams));
    }

    @Test
    public void requestHasAllHeaders() {
        for (int i=0; i<complicatedRequest.getHeaders().length; i++) {
           assertEquals(complicatedRequest.getHeaders()[i].getLine(), headers[i].getLine());
        }
    }

    @Test
    public void returnsDataForGivenHeaderKeyWord() {
        assertEquals("15", complicatedRequest.getHeaderData(RequestHeader.CONTENT_LENGTH.getKeyword()));
    }

    @Test
    public void requestHasData() {
        assertEquals(data, complicatedRequest.getData());
    }

    @Test
    public void tellsIfRequestIsPatchRequest() {
       assertFalse(simpleGet.isPatchRequest());
       assertTrue(complicatedRequest.isPatchRequest());
    }

    @Test
    public void tellsIfRequestIsPartialRequest() {
        assertFalse(simpleGet.isPartialRequest());
        assertTrue(complicatedRequest.isPartialRequest());
    }
}
