package responders.file_responders;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import http_messages.HTTPStatus;
import http_messages.Request;
import http_messages.ResponseHeader;
import io.FileReader;
import javaserver.Directory;
import responders.file_responders.ImageResponder;
import test_helpers.MockDirectory;

public class ImageResponderTest {
    private static ImageResponder responder;
    private static String[] supportedImageMethods = new String[] {"GET"};
    private MockFileReader fileReader = new MockFileReader();

    private static String jpegRoute = "/image.jpeg";
    private Request jpegRequest = new Request.RequestBuilder("GET " + jpegRoute).build();
    private String contentTypeHeader = ResponseHeader.CONTENT_TYPE.getKeyword();

    @BeforeClass
    public static void loadTestResources() throws IOException, DirectoryNotFoundException {
        Directory directory = MockDirectory.getMock();
        responder = new ImageResponder(supportedImageMethods, directory);
    }

    @Before
    public void setImageData() {
        responder.imageData = fileReader.readBytes(new File("file"));
    }

    @AfterClass
    public static void deleteTempFiles() {
       MockDirectory.deleteFiles();
    }

    @Test
    public void testPublicImageStatusLine() {
        assertEquals(responder.getSuccessfulStatusLine(jpegRequest), HTTPStatus.OK.getStatusLine());
    }

    @Test
    public void testImageResponseBody() {
        assertTrue(responder.getBody(jpegRequest) == null);
    }

    @Test
    public void testImageResponseBodyData() {
        assertEquals(responder.getBodyData(jpegRequest, fileReader), fileReader.bytesRead);
    }

    @Test
    public void testContentLengthHeader() {
        assertTrue(responder.getHeaders(jpegRequest)[0].getLine().contains(ResponseHeader.CONTENT_LENGTH.getKeyword()));
    }

    @Test
    public void testJPEGHeader() {
        assertTrue(responder.getHeaders(jpegRequest)[1].getLine().contains(contentTypeHeader + "image/jpeg"));
    }

    @Test
    public void testPNGHeader() {
        String pngRoute = "/image.png";
        Request pngRequest = new Request.RequestBuilder("GET " + pngRoute).build();
        assertTrue(responder.getHeaders(pngRequest)[1].getLine().contains(contentTypeHeader + "image/png"));
    }

    @Test
    public void testGIFHeader() {
        String gifRoute = "/image.gif";
        Request pngRequest = new Request.RequestBuilder("GET " + gifRoute).build();
        assertTrue(responder.getHeaders(pngRequest)[1].getLine().contains(contentTypeHeader + "image/gif"));
    }

    class MockFileReader extends FileReader {
        public byte[] bytesRead;

        @Override
        public byte[] readBytes(File file) {
           this.bytesRead = "some bytes".getBytes();
           return bytesRead;
        }
    }
}