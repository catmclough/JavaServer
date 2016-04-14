package responders.file_responders;

import static org.junit.Assert.*;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.Response;
import javaserver.Directory;
import responders.Responder;
import responders.file_responders.PatchResponder;
import test_helpers.MockDirectory;

public class PatchResponderTest {
    private static String defaultContent = "default content";
    private String patchedContent = "new content";
    private static String textFileRoute;
    private String defaultContentMatchHeader = "If-Match: dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";
    private String nonMatchingEtagHeader = "If-Match: x";

    private static PatchResponder responder;
    private static String[] supportedFileMethods = new String[] {"GET", "PATCH"};

    private String twoHundred = HTTPStatus.OK.getStatusLine();
    private String twoOhFour = HTTPStatus.NO_CONTENT.getStatusLine();

    private Request validPatchRequest = new Request.RequestBuilder(createPatchRequest(defaultContentMatchHeader)).build();
    private static Directory directory;

    @BeforeClass
    public static void createTextFile() throws DirectoryNotFoundException, IOException {
        directory = MockDirectory.getMock();
        textFileRoute = "/" + MockDirectory.txtFile.getName();
        responder = new PatchResponder(supportedFileMethods, directory);
    }

    @After
    public void resetFile() {
        writeDefaultContentsToFile();
    }

    @AfterClass
    public static void deleteFiles() {
        MockDirectory.deleteFiles();
    }

    private static void writeDefaultContentsToFile() {
        try {
            FileWriter writer = new FileWriter(MockDirectory.txtFile);
            writer.write(defaultContent);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createPatchRequest(String ifMatchHeader) {
        return "PATCH " + textFileRoute + System.lineSeparator() + ifMatchHeader
            + System.lineSeparator() + System.lineSeparator() + patchedContent;
    }

    private Response getFile() {
        Responder fileResponder = new TextFileResponder(supportedFileMethods, directory);
        Request request = new Request.RequestBuilder("GET " + textFileRoute).build();
        Response fileResponse = fileResponder.getResponse(request);
        return fileResponse;
    }

    @Test
    public void testGetResponse() {
        assertEquals(getFile().getStatusLine(), twoHundred);
        assertEquals(getFile().getBody(), defaultContent);
    }

    @Test
    public void testNonMatchingEtagDoesNotChangeFileContents() {
        Request invalidPatchRequest = new Request.RequestBuilder(createPatchRequest(nonMatchingEtagHeader)).build();
        responder.getResponse(invalidPatchRequest);
        assertEquals(getFile().getBody(), defaultContent);
    }

    @Test
    public void testPatchResponseCode() {
        Response validPatchResponse = responder.getResponse(validPatchRequest);
        assertEquals(validPatchResponse.getStatusLine(), twoOhFour);
    }

    @Test
    public void TestRecognizesEtagMatch() {
        assertTrue(responder.etagMatchesFileContent(validPatchRequest));
    }

    @Test
    public void writesDataWhenEtagMatches() {
        assertEquals(getFile().getBody(), defaultContent);
        responder.getResponse(validPatchRequest);
        assertEquals(getFile().getBody(), patchedContent);
    }
}
