package responders;

import http_messages.*;
import javaserver.Authenticator;
import javaserver.RequestLog;

public class LogResponder implements Responder {
    private RequestLog requestLog;
    private String[] supportedMethods;
    private String realm = "Cob Spec Logs";

    public LogResponder(String[] supportedMethods) {
        this.supportedMethods = supportedMethods;
        this.requestLog = RequestLog.getInstance();
    }

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request))
            .headers(getHeaders())
            .body(getLog(request))
            .build();
    }

    private String getStatusLine(Request request) {
        if (requestIsSupported(supportedMethods, request.getMethod())  && Authenticator.isAuthorized(request)) {
            return HTTPStatus.OK.getStatusLine();
        } else {
            return HTTPStatus.UNAUTHORIZED.getStatusLine();
        }
    }

    private Header[] getHeaders() {
        Header basicAuth = new Header.HeaderBuilder(ResponseHeader.AUTHENTICATION.getKeyword() + "Basic realm=\"" + realm + "\"").build();
        return new Header[] {basicAuth};
    }

    private String getLog(Request request) {
        String body = "";
        if (requestIsSupported(supportedMethods, request.getMethod()) && Authenticator.isAuthorized(request)) {
            body += requestLog.getLogContents();
        }
        return body;
    }
}
