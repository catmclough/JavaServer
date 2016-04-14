package javaserver;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.AfterClass;
import java.net.Socket;
import java.util.Arrays;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DirectoryNotFoundException;
import http_messages.HTTPStatus;
import io.RequestReader;
import io.SocketWriter;
import routers.CobSpecRouter;
import test_helpers.MockDirectory;

public class ClientWorkerTest {
	private ClientWorker clientWorker;
	private MockClientSocket mockSocket;
	private MockReader mockReader;
	private MockSocketWriter mockWriter;
	private String getRequest = "GET /foo";
	private static Directory mockDirectory;
	private static CobSpecRouter router;

	@BeforeClass
	public static void setup() throws IOException {
	    try {
            mockDirectory = MockDirectory.getMock();
        } catch (DirectoryNotFoundException e) {
            System.err.println("ClientWorkerTest tried to run tests with a non-existant public directory");
            e.printStackTrace();
        }
	    router = new CobSpecRouter(mockDirectory);
	}

	@Before
	public void setUp() throws Exception {
	    mockSocket = new MockClientSocket();
		clientWorker = new ClientWorker(mockSocket, router, mockDirectory);
		mockReader = new MockReader(getRequest);
		mockWriter = new MockSocketWriter();
		clientWorker.reader = mockReader;
		clientWorker.writer = mockWriter;
		clientWorker.run();
	}

	@AfterClass
	public static void tearDown() {
	    MockDirectory.deleteFiles();
	}
	
	@Test
	public void readsAndLogsRequest() {
	    assertTrue(clientWorker.requestLog.getLogContents().contains(getRequest));
	}

	@Test
	public void getsAndSendsFormattedResponseToWriter() {
        String getRequestResponse = HTTPStatus.NOT_FOUND.getStatusLine() + System.lineSeparator() + System.lineSeparator();
		assertTrue(Arrays.equals(getRequestResponse.getBytes(), mockWriter.latestResponse));
	}

	@Test
	public void closesClientSocket() {
	  assertTrue(mockSocket.isClosed());
	}

	class MockClientSocket extends Socket {
		MockClientSocket() {
			super();
		}
	}

	class MockReader extends RequestReader {
	  private String request;

		MockReader(String request) {
			this.request = request;
		}

		@Override
		public void openReader(Socket socket) {
		}

		@Override
		public String readFromSocket() {
			return request;
		}
	}

	class MockSocketWriter extends SocketWriter {
	  public byte[] latestResponse;

		@Override
		public void openWriter(Socket socket) {
		}

		@Override
		public void respond(byte[] response) {
		    this.latestResponse = response;
		}

		@Override
		public void closeOutputStream() {
		}
	}
}
