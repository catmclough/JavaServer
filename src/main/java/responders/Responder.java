package responders;

import java.util.Arrays;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;

public interface Responder {
    public Response getResponse(Request request);

    default public boolean requestIsSupported(String[] supportedMethods, String method) {
        return Arrays.asList(supportedMethods).contains(method);
    }

    default public String getStatusLine(Request request, String[] supportedMethods) {
        if (requestIsSupported(supportedMethods, request.getMethod())) {
            return HTTPStatus.OK.getStatusLine();
        } else {
            return HTTPStatus.NOT_FOUND.getStatusLine();
        }
    }
}
