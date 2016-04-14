package http_messages;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Test;
import http_messages.HTTPStatus;
import http_messages.Response;

public class ResponseTest {

    private Header validHeader = new Header.HeaderBuilder("Content-Length: 100").build();
    private String bodyContent = "some content";
    private String bodyData = "some more content";
    private byte[] bodyDataContent = bodyData.getBytes();
    private Response mockResponse = new Response.ResponseBuilder()
            .statusLine(HTTPStatus.OK.getStatusLine())
            .headers(new Header[] {validHeader})
            .body(bodyContent)
            .bodyData(bodyDataContent)
            .build();

    @Test
    public void hasResponseStatusLine() {
       assertEquals(mockResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void hasResponseHeaders() {
       assertEquals(mockResponse.getHeaders()[0].getLine(), validHeader.getLine());
    }

    @Test
    public void hasResponseBody() {
       assertEquals(mockResponse.getBody(), bodyContent);
    }

    @Test
    public void hasResponseBodyData() {
       assertTrue(Arrays.equals(mockResponse.getBodyData(), bodyDataContent));
    }

    @Test
    public void testResponseFormatting() {
        String properlyFormattedResponse = HTTPStatus.OK.getStatusLine() + System.lineSeparator() +
            validHeader.getLine() + System.lineSeparator() + System.lineSeparator() +
            bodyContent + System.lineSeparator() + bodyData;

        assertTrue(Arrays.equals(mockResponse.formatResponse(), properlyFormattedResponse.getBytes()));
    }

    @Test
    public void formatsResponseWithNullResponseParts() {
        Response mockResponseWithOnlyStatus = new Response.ResponseBuilder()
                .statusLine(HTTPStatus.OK.getStatusLine())
                .build();

        String properlyFormattedSimpleResponse = HTTPStatus.OK.getStatusLine() + System.lineSeparator() +
                System.lineSeparator();

        assertTrue(Arrays.equals(mockResponseWithOnlyStatus.formatResponse(), properlyFormattedSimpleResponse.getBytes()));
    }
}
