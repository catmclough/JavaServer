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
import http_messages.ResponseHeader;
import javaserver.Directory;
import responders.file_responders.FileResponder;
import test_helpers.MockDirectory;

public class TextFileResponderTest {
    private static String textFileRoute;
    private static String textFileContents;
    private static String[] supportedMethods = new String[] {"GET", "PATCH"};
    private static FileResponder responder;
    private Request simpleGet = new Request.RequestBuilder("GET " + textFileRoute).build();
    private Request partialFileRequest = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=0-4").build();
    private Request patchFileRequest = new Request.RequestBuilder("PATCH " + textFileRoute).build();

    @BeforeClass
    public static void createTextFile() throws IOException, DirectoryNotFoundException {
        Directory directory = MockDirectory.getMock();
        textFileRoute = "/" + MockDirectory.txtFile.getName();
        textFileContents = MockDirectory.txtFileContents;
        responder = new TextFileResponder(supportedMethods, directory);
    }

    @AfterClass
    public static void deleteFiles() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void simpleFileRequestResponseCode() {
        Response fileResponse = responder.getResponse(simpleGet);
        assertEquals(fileResponse.getStatusLine(), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testMethodNotAllowedResponseCode() {
        Request postFile = new Request.RequestBuilder("POST " + textFileRoute).build();
        Response unallowedRequestResponse = responder.getResponse(postFile);
        assertEquals(unallowedRequestResponse.getStatusLine(), HTTPStatus.METHOD_NOT_ALLOWED.getStatusLine());
    }


    @Test
    public void partialFileRequestResponseCode() {
        Response fileResponse = responder.getResponse(partialFileRequest);
        assertEquals(fileResponse.getStatusLine(), HTTPStatus.PARTIAL_CONTENT.getStatusLine());
    }

    @Test
    public void patchFileRequestResponseCode() {
        Response fileResponse = responder.getResponse(patchFileRequest);
        assertEquals(fileResponse.getStatusLine(), HTTPStatus.NO_CONTENT.getStatusLine());
    }

    @Test
    public void partialContentHasCorrectHeaders() {
       Response fileResponse = responder.getResponse(partialFileRequest);
       String partialContentLength = "5";
       assertEquals(fileResponse.getHeaders()[0].getLine(), ResponseHeader.CONTENT_LENGTH.getKeyword() + partialContentLength);
    }

    @Test
    public void testFileContentsInBody() {
        Request getFile = new Request.RequestBuilder("GET " + textFileRoute).build();
        Response existingFileResponse = responder.getResponse(getFile);
        assertTrue(existingFileResponse.getBody().contains(MockDirectory.txtFileContents));
    }

    @Test
    public void testPartialContentWithFullRange() {
        String firstFiveBytes = textFileContents.substring(0, 5);
        Request fullRangeRequest = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=0-4").build();
        Response partialResponse = responder.getResponse(fullRangeRequest);
        assertEquals(partialResponse.getBody(), firstFiveBytes);
    }

    @Test
    public void testPartialContentWithEndOfRange() {
        Request request = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=-6").build();
        String lastSixBytes = textFileContents.substring(textFileContents.length() - 6);
        Response partialResponse = responder.getResponse(request);
        assertEquals(partialResponse.getBody(), lastSixBytes);
    }

    @Test
    public void testPartialContentWithStartOfRange() {
        Request startOfRangeRequest = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=4-").build();
        String bytesFrom4 = textFileContents.substring(4);
        Response response = responder.getResponse(startOfRangeRequest);
        assertEquals(response.getBody(), bytesFrom4);
    }
}