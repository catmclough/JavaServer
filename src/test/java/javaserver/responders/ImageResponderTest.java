package javaserver.responders;

import static org.junit.Assert.*;

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

    @Test
    public void testImageResponderCreation() {
        Responder responder = Routes.getResponder("/" + existingJpegImage);
        assertEquals(responder.getClass(), ImageResponder.class);
    }
    
    @Test
    public void testPublicImageStatusLine() {
        Responder responder = Routes.getResponder("/" + existingJpegImage);
        Response imageResponse = responder.getResponse(jpegRequest);
        assertEquals(imageResponse.getResponseCode(), HTTPStatusCode.TWO_HUNDRED.getStatusLine());
    }

    @Test
    public void testUnknownImageStatusLine() {
        Responder responder = Routes.getResponder(unknownImagePath);
        Response imageResponse = responder.getResponse(bogusRequest);
        assertEquals(imageResponse.getResponseCode(), HTTPStatusCode.FOUR_OH_FOUR.getStatusLine());
    }

    @Test
    public void testContentLengthHeaderExists() {
       Responder responder = Routes.getResponder("/" + existingJpegImage);
       Response imageResponse = responder.getResponse(jpegRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Length: "));
    }

    @Test
    public void testJPEGHeader() {
       Responder responder = Routes.getResponder("/" + existingJpegImage);
       Response imageResponse = responder.getResponse(jpegRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Type: image/jpeg"));
    }
    
    @Test
    public void testPNGHeader() {
       Responder responder = Routes.getResponder("/" + existingPNGImage);
       Request pngRequest = RequestParser.createRequest("GET /" + existingPNGImage);
       Response imageResponse = responder.getResponse(pngRequest);
       assertTrue(imageResponse.getHeader().contains("Content-Type: image/png"));
    }

//    @Test
//    public void testImageResponseBody() {
//       Responder responder = Routes.getResponder("/" + existingPNGImage);
//       Request validRequest = RequestParser.createRequest("GET /" + existingPNGImage);
//       Response imageResponse = responder.getResponse(validRequest); 
//       assertTrue(imageResponse.getBody().equals()
//    }
}
