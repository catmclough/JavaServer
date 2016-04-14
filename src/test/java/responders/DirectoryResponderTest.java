package responders;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import http_messages.HTMLContent;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import http_messages.ResponseHeader;
import javaserver.Directory;
import responders.DirectoryResponder;
import responders.Responder;
import test_helpers.MockDirectory;

public class DirectoryResponderTest {
    private String directoryRoute = "/";
    private static String[] supportedDirectoryMethods = new String[] {"GET"};

    private Request supportedRequest = new Request.RequestBuilder("GET " + directoryRoute).build();
    private static Directory directory;
    private static Responder responder;

    @BeforeClass
    public static void setupPublicDirectory() throws IOException, DirectoryNotFoundException {
        directory = MockDirectory.getMock();
        responder = new DirectoryResponder(supportedDirectoryMethods, directory);
    }

    @AfterClass
    public static void deleteMockDirectory() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testDirectoryResponderCreation() {
        assertEquals(responder.getClass(), DirectoryResponder.class);
    }

    @Test
    public void testRespondsWith200() {
        Response response = responder.getResponse(supportedRequest);
        assertEquals(response.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testRespondsWith404() {
        Request unsupportedRequest = new Request.RequestBuilder("POST " + directoryRoute).build();
        Response unsupportedRequestResponse = responder.getResponse(unsupportedRequest);
        assertEquals(unsupportedRequestResponse.getStatusLine(), HTTPStatus.NOT_FOUND.getStatusLine());
    }

    @Test
    public void testRespondsWithContentTypeHeader() {
        Response response = responder.getResponse(supportedRequest);
        assertEquals(response.getHeaders()[0].getLine(), ResponseHeader.CONTENT_TYPE.getKeyword() + "text/html;");
    }

    @Test
    public void testDirectoryLinksBody() {
        Response response = responder.getResponse(supportedRequest);
        String responseBody = response.getBody();

        String listOfDirectoryLinks = HTMLContent.listOfLinks(directory.getFileNameList());
        assertTrue(responseBody.contains(listOfDirectoryLinks));
    }
}
