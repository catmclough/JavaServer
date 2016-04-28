package responders;

import static org.junit.Assert.*;
import org.junit.Test;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import responders.RedirectResponder;

public class RedirectResponderTest {
    private String redirectRoute = "/redirect";
    private String redirectLocation = "http://localhost:5000/" ;
    private String redirectHeader = "Location: " + redirectLocation;
    private Request supportedRequest = new Request.RequestBuilder("GET " + redirectRoute).build();

    private String[] supportedMethods = new String[] {"GET"};
    private RedirectResponder responder = new RedirectResponder(supportedMethods, redirectLocation);

    @Test
        public void testRedirectResponderCreation() {
        assertEquals(responder.getClass(), RedirectResponder.class);
    }

    @Test
    public void testRedirectRespondsWith302() {
        Response response = responder.getResponse(supportedRequest);
        assertEquals(response.getStatusLine(), HTTPStatus.FOUND.getStatusLine());
    }

    @Test
    public void testInvalidRedirectRespondsWith404() {
        Request unsupportedRequest = new Request.RequestBuilder("POST " + redirectRoute).build();
        Response response = responder.getResponse(unsupportedRequest);
        assertEquals(response.getStatusLine(), HTTPStatus.NOT_FOUND.getStatusLine());
    }

    @Test
    public void testRedirectHeader() {
        Response redirectResponse = responder.getResponse(supportedRequest);
        assertTrue(redirectResponse.getHeaders()[0].getLine().contains(redirectHeader));
    }
}
