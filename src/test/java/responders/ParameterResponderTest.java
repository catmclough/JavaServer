package responders;

import static org.junit.Assert.*;
import org.junit.Test;

import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import responders.ParameterResponder;

public class ParameterResponderTest {
    private String codedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff";
    private Request requestWithParams = new Request.RequestBuilder("GET " + codedURI).build();
    private String[] supportedParameterMethods = new String[] {"GET"};
    private ParameterResponder responder = new ParameterResponder(supportedParameterMethods);

    @Test
    public void testParameterResponderCreation() {
        assertEquals(responder.getClass(), ParameterResponder.class);
    }

    @Test
    public void testValidCodedParametersResponseCode() {
        Response validParamsResponse = responder.getResponse(requestWithParams);
        assertEquals(validParamsResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testInValidRequestResponseCode() {
        Request requestWithInvalidParams = new Request.RequestBuilder("POST " + codedURI).build();
        Response invalidParamsResponse = responder.getResponse(requestWithInvalidParams);
        assertEquals(invalidParamsResponse.getStatusLine(), HTTPStatus.NOT_FOUND.getStatusLine());
    }

    @Test
    public void testUnrecognizedParamsInResponseBody() {
        Request requestWithRandomParams = new Request.RequestBuilder("GET /random_params?foo=bar").build();
        Response nonCodedParamResponse = responder.getResponse(requestWithRandomParams);
        assertEquals(nonCodedParamResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testDecodedParamsInResponseBody() {
        String decodedParamOne = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        String decodedParamTwo = "variable_2 = stuff";

        Response validRequestWithParamsResponse = responder.getResponse(requestWithParams);
        String responseBody = validRequestWithParamsResponse.getBody();

        assertTrue(responseBody.contains(decodedParamOne));
        assertTrue(responseBody.contains(decodedParamTwo));
    }
}
