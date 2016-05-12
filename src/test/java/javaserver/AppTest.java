package javaserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import factories.ServerCreator;
import routers.Router;

import org.junit.After;
import org.junit.Before;
import exceptions.DirectoryNotFoundException;
import static org.junit.Assert.*;

public class AppTest {
    private String[] emptyArgs = new String[] {};

    @Before
    public void setUp() throws IOException {
        App.serverCreator = new MockServerCreator();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws IOException {
        if (App.server != null) {
            App.server.shutDown();
        }
    }

    public void testSetsDefaultPort() throws IOException, DirectoryNotFoundException {
        App.main(emptyArgs);
        assertEquals(App.port, App.DEFAULT_PORT);
    }

    public void testSetsValidPortSpecifiedInArgs() throws IOException, DirectoryNotFoundException {
        App.main(new String[] {"-P", "8080"});
        assertEquals(App.port, 8080);
    }

    public void testSetsDefaultPortWhenInvalidPortIsSpecifiedInArgs() throws IOException, DirectoryNotFoundException {
        App.main(new String[] {"-P", "XYZ"});
        assertEquals(App.port, App.DEFAULT_PORT);
    }

    public void testSetsDefaultDirectory() throws IOException, DirectoryNotFoundException {
        App.main(emptyArgs);
        String defaultPublicDir = "public/";
        assertEquals(App.DEFAULT_DIRECTORY_NAME, defaultPublicDir);
    }

    public void testServerCreation() throws IOException, DirectoryNotFoundException {
        App.main(emptyArgs);
        assertNotNull(App.serverCreator);
        assertNotNull(App.server);
    }

    public void testRunsServer() throws IOException, DirectoryNotFoundException {
        App.main(emptyArgs);
        assertTrue(App.server.isOn() == true);
    }
}

class MockServerCreator extends ServerCreator {

    @Override
    public Server createServer(int port, Router router) throws IOException {
        return new MockServer(new ServerSocket(port), router);
    }
}

class MockServer extends Server {

    MockServer(ServerSocket serverSocket, Router router) {
        super(serverSocket, router);
    }

    @Override
    public void run() throws IOException {
        this.isOn = true;
    }
}
