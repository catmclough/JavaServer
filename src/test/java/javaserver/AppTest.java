package javaserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import org.junit.After;
import org.junit.Before;
import junit.framework.TestCase;

public class AppTest extends TestCase{

	private ServerSocket socket;
	private MockServer mockServer;
	private String[] emptyArgs = new String[] {};
	private String existingDirectory = "public";
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws IOException {
		this.socket = new ServerSocket();
		this.mockServer = new MockServer(socket);
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws IOException {
		if (App.server != null)
			App.server.shutDown();
		System.setOut(null);
	}

	public void testSetsDefaultPort() throws IOException {
		App.setUpServer(emptyArgs);
		assertEquals(App.port, App.DEFAULT_PORT);
		App.server.shutDown();

		App.setUpServer(new String[] {"-P"});
		assertEquals(App.port, App.DEFAULT_PORT);
	}

	public void testSpecifiedPortInArgs() throws IOException {
		App.setUpServer(new String[] {"-P", "8080"});
		assertEquals(App.port, 8080);
	}

	public void testInvalidSpecifiedPortInArgs() throws IOException {
		App.setUpServer(new String[] {"-P", "XYZ"});
		assertEquals(App.port, App.DEFAULT_PORT);
	}

	public void testSetsDefaultPublicDirectory() throws IOException {
		App.setUpServer(new String[] {"-D"});
		assertEquals(App.publicDirectory.getDirectoryName(), App.DEFAULT_PUBLIC_DIRECTORY);
	}

	public void testUnrecognizedDirectoryMessage() throws IOException {
		boolean errorCaught = false;
		try {
			App.setUpServer(new String[] {"-D", "/non_existant_directory"});
		} catch (Error e) {
			errorCaught = true;
		}

		assertTrue(errorCaught);
	}

	public void testRecognizesExistingDirectory() throws IOException {
		App.setUpServer(new String[] {"-D", existingDirectory});
		assertEquals(App.publicDirectory.getDirectoryName(), existingDirectory);
	}

	public void testSetsPublicDirectoryInRoutes() throws IOException {
		App.setUpServer(new String[] {"-D", existingDirectory});
		assertEquals(App.publicDirectory.getDirectoryName(), existingDirectory);
	}

	public void testServerCreation() throws IOException {
		App.setUpServer(emptyArgs);
		assertNotNull(App.server);
	}

	public void testRunsServer() throws IOException {
		App.runServer(mockServer);
		assertTrue(mockServer.isRunning = true);
	}
}

class MockServer extends Server {

	public boolean isRunning = false;

	MockServer(ServerSocket socket) {
		super(socket);
	}

	@Override
	public void run() throws IOException {
		this.isRunning = true;
	}
}
