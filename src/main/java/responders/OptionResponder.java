package responders;

import http_messages.Header;
import http_messages.Request;
import http_messages.Response;
import http_messages.ResponseHeader;

public class OptionResponder implements Responder {
    private String[] supportedMethods;

    public OptionResponder(String[] supportedMethods) {
        this.supportedMethods = supportedMethods;
    }

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request, supportedMethods))
            .headers(getHeaders(request))
            .build();
    }

    private Header[] getHeaders(Request request) {
        if (request.getMethod().equals("OPTIONS")) {
            String headerLine = ResponseHeader.OPTIONS.getKeyword();
            headerLine += String.join(",", supportedMethods);
            Header optionHeader = new Header.HeaderBuilder(headerLine).build();
            return new Header[] {optionHeader};
        }
        return null;
    }
}
