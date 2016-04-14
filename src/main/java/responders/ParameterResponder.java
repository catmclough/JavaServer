package responders;

import http_messages.Request;
import http_messages.Response;
import text_parsers.ParameterParser;

public class ParameterResponder implements Responder {
    private String[] supportedMethods;

    public ParameterResponder(String[] supportedMethods) {
        this.supportedMethods = supportedMethods;
    }

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request, supportedMethods))
            .body(ParameterParser.getParamBody(request.getParams()))
            .build();
    }
}
