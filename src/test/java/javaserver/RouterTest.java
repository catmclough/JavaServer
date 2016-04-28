package javaserver;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import exceptions.DirectoryNotFoundException;
import http_messages.Request;
import javaserver.Directory;
import responders.ErrorResponder;
import responders.FormResponder;
import responders.ParameterResponder;
import responders.Responder;
import responders.file_responders.TextFileResponder;
import javaserver.Router;
import routes.CobSpecRoutes;
import test_helpers.MockDirectory;

public class RouterTest {
    private static Router router;
    private static Directory directory;

    private String textFileRoute = "/" + MockDirectory.txtFile.getName();
    private Request formRouteRequest = new Request.RequestBuilder("GET /form").build();

    @BeforeClass
    public static void setup() throws DirectoryNotFoundException, IOException {
        directory = MockDirectory.getMock();
        CobSpecRoutes routes = new CobSpecRoutes(directory);
        router = new Router(routes.getRoutesAndResponders());
    }

    @AfterClass
    public static void tearDown() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testCreatesErrorResponderForUnknownRoute() {
        Request unknownRouteRequest = new Request.RequestBuilder("GET /foobar").build();
        Responder responder = router.getResponder(unknownRouteRequest);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testCreatesErrorResponderForUnknownRouteWithParams() {
        Request unknownRequestWithParams = new Request.RequestBuilder("GET /foobar?var1=xyz").build();
        Responder responder = router.getResponder(unknownRequestWithParams);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testCreatesErrorResponderForUnknownRouteWithPartialRange() {
        Request unknownFileRequestWithPartial = new Request.RequestBuilder("GET /foobar\nRange: bytes=0-10").build();
        Responder responder = router.getResponder(unknownFileRequestWithPartial);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testFormResponderCreation() {
        Responder responder = router.getResponder(formRouteRequest);
        assertEquals(responder.getClass(), FormResponder.class);
    }

    @Test
    public void testCreatesFormWithEmptyDataLine() {
        Responder responder = router.getResponder(formRouteRequest);
        assertTrue(responder.getResponse(formRouteRequest).getBody().isEmpty());
    }

    @Test
    public void testCreatesParameterResponder() {
        Request paramRequest = new Request.RequestBuilder("GET /parameters").build();
        Responder responder = router.getResponder(paramRequest);
        assertEquals(responder.getClass(), ParameterResponder.class);
    }

    @Test
    public void testCreateTextFileResponder() {
        Request getPartial = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=0-4").build();
        Responder responder = router.getResponder(getPartial);
        assertEquals(responder.getClass(), TextFileResponder.class);
    }
}