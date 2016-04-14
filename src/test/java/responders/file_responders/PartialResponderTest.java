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
import responders.file_responders.PartialResponder;
import test_helpers.MockDirectory;

public class PartialResponderTest {
    private static String textFileRoute;
    private static String textFileContents;
    private static PartialResponder responder;
    private static String[] supportedFileMethods = new String[] {"GET", "PATCH"};

    private String twoOhSix = HTTPStatus.PARTIAL_CONTENT.getStatusLine();
    private Request fullRangeRequest = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=0-4").build();

    private static Directory directory;

    @BeforeClass
    public static void createTextFile() throws IOException, DirectoryNotFoundException {
        directory = MockDirectory.getMock();
        textFileContents = MockDirectory.txtFileContents;
        textFileRoute = "/" + MockDirectory.txtFile.getName();
        responder = new PartialResponder(supportedFileMethods, directory);
    }

    @AfterClass
    public static void deleteFiles() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testFullRangePartialRequestResponseCode() {
        Response partial = responder.getResponse(fullRangeRequest);
        assertEquals(partial.getStatusLine(), twoOhSix);
    }

    @Test
    public void testInvalidPartialRequestResponseCode() {
        Request invalidRequest = new Request.RequestBuilder("POST " + textFileRoute + "\nRange: bytes=0-4").build();
        Response partial = responder.getResponse(invalidRequest);
        assertEquals(partial.getStatusLine(), HTTPStatus.METHOD_NOT_ALLOWED.getStatusLine());
    }

    @Test
    public void testHalfOfRangeRequestResponseCode() {
        Request noBeginningPartial = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=-4").build();
        Request noEndPointPartial = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=4-").build();
        Response noBeginningPartialResponse = responder.getResponse(noBeginningPartial);
        assertEquals(noBeginningPartialResponse.getStatusLine(), twoOhSix);

        Response noEndPartialResponse = responder.getResponse(noEndPointPartial);
        assertEquals(noEndPartialResponse.getStatusLine(), twoOhSix);
    }

    @Test
    public void testPartialContentWithFullRange() {
        String firstFiveBytes = textFileContents.substring(0, 5);
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
