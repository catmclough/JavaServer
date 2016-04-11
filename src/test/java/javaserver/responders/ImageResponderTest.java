package javaserver.responders;

import static org.junit.Assert.*;
import java.io.File;
import java.util.Arrays;
import org.junit.Test;
import javaserver.HTTPStatusCode;
import javaserver.Request;
import javaserver.RequestParser;
import javaserver.Response;
import javaserver.Routes;

public class ImageResponderTest {
    String publicDirectoryPath = "public/";
    String unknownImagePath = "/non-existant-image";
    String existingJpegImage = "image.jpeg";
    String existingPNGImage = "image.png";
    Request jpegRequest = RequestParser.createRequest("GET /" + existingJpegImage);
    Request bogusRequest = RequestParser.createRequest("GET /" + unknownImagePath);

   ImageResponder responder = new ImageResponder(new String[] {"GET"}, new File("public"));

    @Test
    public void testImageResponderCreation() {
        Responder responder = Routes.getResponder("/" + existingJpegImage);
        assertEquals(responder.getClass(), ImageResponder.class);
    }

    @Test
    public void testPublicImageStatusLine() {
        Response imageResponse = responder.getResponse(jpegRequest);
        assertEquals(imageResponse.getResponseCode(), HTTPStatusCode.TWO_HUNDRED.getStatusLine());
    }

    @Test
    public void testUnknownImageStatusLine() {
        Response imageResponse = responder.getResponse(bogusRequest);
        assertEquals(imageResponse.getResponseCode(), HTTPStatusCode.FOUR_OH_FOUR.getStatusLine());
    }

    @Test
    public void testContentLengthHeaderExists() {
       Response imageResponse = responder.getResponse(jpegRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Length: "));
    }

    @Test
    public void testJPEGHeader() {
       Response imageResponse = responder.getResponse(jpegRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Type: image/jpeg"));
    }

    @Test
    public void testPNGHeader() {
       Request pngRequest = RequestParser.createRequest("GET /" + existingPNGImage);
       Response imageResponse = responder.getResponse(pngRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Type: image/png"));
    }

    @Test
    public void testImageResponseBody() {
       Request validRequest = RequestParser.createRequest("GET /" + existingPNGImage);
       Response imageResponse = responder.getResponse(validRequest);
       assertTrue(Arrays.equals(imageResponse.getBodyData(), responder.getImageData(validRequest)));
    }
}
