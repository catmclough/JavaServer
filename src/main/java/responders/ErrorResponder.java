package responders;

import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;

public class ErrorResponder implements Responder {

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request))
            .build();
    }

    private String getStatusLine(Request request) {
        return HTTPStatus.NOT_FOUND.getStatusLine();
    }
}
