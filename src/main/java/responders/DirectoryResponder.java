package responders;

import http_messages.HTMLContent;
import http_messages.Header;
import http_messages.Request;
import http_messages.Response;
import http_messages.ResponseHeader;
import javaserver.Directory;

public class DirectoryResponder implements Responder {
    private String[] supportedMethods;
    private Directory directory;

    public DirectoryResponder(String[] supportedMethods, Directory publicDir) {
        this.supportedMethods = supportedMethods;
        this.directory = publicDir;
    }

    @Override
    public Response getResponse(Request request) {
        return new Response.ResponseBuilder()
            .statusLine(getStatusLine(request, supportedMethods))
            .headers(getHeaders())
            .body(getDirectoryLinksHTML())
            .build();
    }

    private Header[] getHeaders() {
       Header contentType = new Header.HeaderBuilder(ResponseHeader.CONTENT_TYPE.getKeyword() + HTMLContent.contentType + ";").build(); 
       return new Header[] {contentType};
    }

    private String getDirectoryLinksHTML() {
        return HTMLContent.openHTMLAndBody(directory.getName()) + HTMLContent.listOfLinks(directory.getFileNameList()) + HTMLContent.closeBodyAndHTML();
    }
}
