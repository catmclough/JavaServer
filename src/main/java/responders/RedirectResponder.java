package responders;

import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import http_messages.Response;
import http_messages.ResponseHeader;

public class RedirectResponder implements Responder {
    private String[] supportedMethods;
    private String redirectLocation;

    public RedirectResponder(String[] supportedMethods, String redirectLocation) {
        this.supportedMethods = supportedMethods;
        this.redirectLocation = redirectLocation;
    }

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request))
            .headers(getHeaders(request))
            .build();
    }

    private String getStatusLine(Request request) {
        if (requestIsSupported(supportedMethods, request.getMethod())) {
            return HTTPStatus.FOUND.getStatusLine();
        } else {
            return HTTPStatus.NOT_FOUND.getStatusLine();
        }
    }

    private Header[] getHeaders(Request request) {
        if (requestIsSupported(supportedMethods, request.getMethod())) {
            Header redirectHeader = new Header.HeaderBuilder(ResponseHeader.REDIRECT.getKeyword() + redirectLocation).build();
            return new Header[] {redirectHeader};
        }
        return null;
    }
}