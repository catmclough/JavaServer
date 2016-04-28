package responders.file_responders;

import http_messages.HTTPStatus;
import http_messages.Header;
import http_messages.Request;
import http_messages.Response;
import io.FileReader;
import javaserver.Directory;
import responders.Responder;

public abstract class FileResponder implements Responder {
    protected String[] supportedMethods;
    protected Directory directory;
    private FileReader fileReader;
    public abstract String getSuccessfulStatusLine(Request request);
    public abstract Header[] getHeaders(Request request);
    public abstract String getBody(Request request);
    public abstract byte[] getBodyData(Request request, FileReader reader);

    public FileResponder(String[] supportedMethods, Directory directory) {
        this.supportedMethods = supportedMethods;
        this.directory = directory;
        this.fileReader = new FileReader();
    }

    @Override
    public Response getResponse(Request request) {
        if (requestIsSupported(supportedMethods, request.getMethod())) {
            return new Response.ResponseBuilder()
                .statusLine(getSuccessfulStatusLine(request))
                .body(getBody(request))
                .bodyData(getBodyData(request, fileReader))
                .headers(getHeaders(request))
                .build();
        } else {
            return new Response.ResponseBuilder()
                .statusLine(HTTPStatus.METHOD_NOT_ALLOWED.getStatusLine())
                .build();
        }
    }
}