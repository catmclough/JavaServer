package responders;

import static org.junit.Assert.*;
import org.junit.Test;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import responders.OptionResponder;

public class OptionResponderTest {
    private String optionRoute = "/method_options";
    private String[] supportedOptionMethods = new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"};
    private String methodOptionsHeader = "Allow: " + String.join(",", supportedOptionMethods);

    private OptionResponder responder = new OptionResponder(supportedOptionMethods);

    private Request methodOptionsRequest = new Request.RequestBuilder("OPTIONS " + optionRoute).build();
    private Response optionsResponse = responder.getResponse(methodOptionsRequest);

    private Request getRequest = new Request.RequestBuilder("GET " + optionRoute).build();
    private Response getResponse = responder.getResponse(getRequest);

    @Test
    public void testValidOptionResponseCodes() {
        assertEquals(optionsResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testInvalidOptionResponseCode() {
        Request invalidMethodOptionsRequest = new Request.RequestBuilder("PATCH " + optionRoute).build();
        Response invalidMethodResponse = responder.getResponse(invalidMethodOptionsRequest);
        assertEquals(invalidMethodResponse.getStatusLine(), HTTPStatus.NOT_FOUND.getStatusLine());
    }

    @Test
    public void testOptionsHeader() {
        assertEquals(optionsResponse.getHeaders()[0].getLine(), methodOptionsHeader); }

    @Test
    public void testOtherSupportedMethodResponseCode() {
        assertEquals(getResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testUnsupportedMethodHeader() {
        assertNull(getResponse.getHeaders());
    }
}
