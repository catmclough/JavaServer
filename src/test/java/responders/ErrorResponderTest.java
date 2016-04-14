package responders;

import static org.junit.Assert.*;
import org.junit.Test;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import responders.ErrorResponder;

public class ErrorResponderTest {
    private String fourOhFour = HTTPStatus.NOT_FOUND.getStatusLine();

    private ErrorResponder responder = new ErrorResponder();

    @Test
    public void test404ResponseCode() {
        Request unsupportedRequest = new Request.RequestBuilder("GET /foobar").build();
        Response unsupportedRequestResponse = responder.getResponse(unsupportedRequest);
        assertEquals(unsupportedRequestResponse.getStatusLine(), fourOhFour);
    }

    @Test
    public void testRespondsToRequestiWithoutURI() {
        Request noURIRequest = new Request.RequestBuilder("GET").build();
        Response response = responder.getResponse(noURIRequest);
        assertEquals(response.getStatusLine(), fourOhFour);
    }

    @Test
    public void testRespondsToBlankRequest() {
        Request emptyRequest = new Request.RequestBuilder("").build();
        Response response = responder.getResponse(emptyRequest);
        assertEquals(response.getStatusLine(), fourOhFour);
    }

    @Test
    public void testRespondsToCrazyRequest() {
        Request crazyRequest = new Request.RequestBuilder("FOO /BAR").build();
        Response response = responder.getResponse(crazyRequest);
        assertEquals(response.getStatusLine(), fourOhFour);
    }

    @Test
    public void testRespondsToInvalidCodedParams() {
        String unknownRouteWithParams = "/foobar?var1=xyz";
        Request unknownRouteWithParamsRequest = new Request.RequestBuilder("GET " + unknownRouteWithParams).build();
        Response unknownRouteResponse = responder.getResponse(unknownRouteWithParamsRequest);
        assertEquals(unknownRouteResponse.getStatusLine(), fourOhFour);
    }

    @Test
    public void testInvalidiFileRequestWithPartial() {
        String unknownFileWithPartial = "/foobar\nRange: bytes=0-10";
        Request unknownFileWithPartialRequest = new Request.RequestBuilder("GET " + unknownFileWithPartial).build();
        Response invalidRangeResponse = responder.getResponse(unknownFileWithPartialRequest);
        assertEquals(invalidRangeResponse.getStatusLine(), fourOhFour);
    }
}
