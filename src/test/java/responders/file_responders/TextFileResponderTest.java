package responders.file_responders;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import javaserver.Directory;
import responders.file_responders.FileResponder;
import test_helpers.MockDirectory;

public class TextFileResponderTest {
    private static String textFileRoute;
    private static String[] supportedMethods = new String[] {"GET", "PATCH"};
    private static FileResponder responder;

    @BeforeClass
    public static void createTextFile() throws IOException, DirectoryNotFoundException {
        Directory directory = MockDirectory.getMock();
        textFileRoute = "/" + MockDirectory.txtFile.getName();
        responder = new TextFileResponder(supportedMethods, directory);
    }

    @AfterClass
    public static void deleteFiles() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testSupportedFileRequestResponseCode() {
        Request getFile = new Request.RequestBuilder("GET " + textFileRoute).build();
        Response fileResponse = responder.getResponse(getFile);
        assertEquals(fileResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testMethodNotAllowedResponseCode() {
        Request postFile = new Request.RequestBuilder("POST " + textFileRoute).build();
        Response unallowedRequestResponse = responder.getResponse(postFile);
        assertEquals(unallowedRequestResponse.getStatusLine(), HTTPStatus.METHOD_NOT_ALLOWED.getStatusLine());
    }

    @Test
    public void testFileContentsInBody() {
        Request getFile = new Request.RequestBuilder("GET " + textFileRoute).build();
        Response existingFileResponse = responder.getResponse(getFile);
        assertTrue(existingFileResponse.getBody().contains(MockDirectory.txtFileContents));
    }
}
