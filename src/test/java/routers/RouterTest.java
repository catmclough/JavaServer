package routers;

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
import responders.file_responders.PartialResponder;
import responders.file_responders.PatchResponder;
import routers.CobSpecRouter;
import test_helpers.MockDirectory;

public class RouterTest {
    private static CobSpecRouter router;
    private static Directory directory;

    private String textFileRoute = "/" + MockDirectory.txtFile.getName();
    private Request formRouteRequest = new Request.RequestBuilder("GET /form").build();

    @BeforeClass
    public static void setup() throws DirectoryNotFoundException, IOException {
        directory = MockDirectory.getMock();
        router = new CobSpecRouter(directory);
    }

    @AfterClass
    public static void tearDown() {
        MockDirectory.deleteFiles();
    }

    @Test
    public void testCreatesErrorResponderForUnknownRoute() {
        Request unknownRouteRequest = new Request.RequestBuilder("GET /foobar").build();
        Responder responder = router.getResponder(unknownRouteRequest, directory);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testCreatesErrorResponderForUnknownRouteWithParams() {
        Request unknownRequestWithParams = new Request.RequestBuilder("GET /foobar?var1=xyz").build();
        Responder responder = router.getResponder(unknownRequestWithParams, directory);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testCreatesErrorResponderForUnknownRouteWithPartialRange() {
        Request unknownFileRequestWithPartial = new Request.RequestBuilder("GET /foobar\nRange: bytes=0-10").build();
        Responder responder = router.getResponder(unknownFileRequestWithPartial, directory);
        assertEquals(responder.getClass(), ErrorResponder.class);
    }

    @Test
    public void testFormResponderCreation() {
        Responder responder = router.getResponder(formRouteRequest, directory);
        assertEquals(responder.getClass(), FormResponder.class);
    }

    @Test
    public void testCreatesFormWithEmptyDataLine() {
        Responder responder = router.getResponder(formRouteRequest, directory);
        assertTrue(responder.getResponse(formRouteRequest).getBody().isEmpty());
    }

    @Test
    public void testCreatesParameterResponder() {
        Request paramRequest = new Request.RequestBuilder("GET /parameters").build();
        Responder responder = router.getResponder(paramRequest, directory);
        assertEquals(responder.getClass(), ParameterResponder.class);
    }

    @Test
    public void testCreatesPartialResponder() {
        Request getPartial = new Request.RequestBuilder("GET " + textFileRoute + "\nRange: bytes=0-4").build();
        Responder responder = router.getResponder(getPartial, directory);
        assertEquals(responder.getClass(), PartialResponder.class);
    }

    @Test
    public void testCreatesPatchResponder() {
        Request patchContent = new Request.RequestBuilder("PATCH " + textFileRoute + "\nIf-Match: xyz").build();
        Responder responder = router.getResponder(patchContent, directory);
        assertEquals(responder.getClass(), PatchResponder.class);
    }
}
