package javaserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ClientWorkerTest extends TestCase {
	Socket testClientSocket;
	ClientWorker testClientWorker;
	String simpleGet = "GET / HTTP/1.1\r\n";
	String blankRequest = "";

	@Before
	public void setUp() throws Exception {
  		App.configureRoutes();
	    Reader reader = new Reader(stubRequestReader(simpleGet));
	    MockSocketWriter mockWriter = new MockSocketWriter(mockOutputStream());
	    testClientWorker = new ClientWorker(reader, mockWriter);
	}

	@Test
	public void testRun() throws IOException {
	  testClientWorker.run();
	  String twoHundred = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	  assertTrue(testClientWorker.writer.latestResponse.contains(twoHundred));
	}

	@Test
	public void respondsToInvalidRequest() {
		Request blankRequest = new Request("", "");
		testClientWorker.respond(blankRequest);
		String fourOhFour = HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
		assertTrue(testClientWorker.writer.latestResponse.contains(fourOhFour));
	}

	@Test
	public void respondsToUnSupportedRequest() {
		Request unsupportedRequest = new Request("SPRINKLE", "/chocolate/sprinkles");
		testClientWorker.respond(unsupportedRequest);
		String fourOhFour = HTTPStatusCode.FOUR_OH_FOUR.getStatusLine();
		assertTrue(testClientWorker.writer.latestResponse.contains(fourOhFour));
	}

	private BufferedReader stubRequestReader(String requestLine) {
		InputStream stubInputStreamWithGet = new ByteArrayInputStream(requestLine.getBytes());
		InputStreamReader inputReader = new InputStreamReader(stubInputStreamWithGet);
		return new BufferedReader(inputReader);
	}

	private DataOutputStream mockOutputStream() {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		return new DataOutputStream(outputStream);
	}
}

class MockSocketWriter extends SocketWriter {
	 MockSocketWriter(DataOutputStream mockOutputStream) {
		 super(mockOutputStream);
	 }

	 @Override
	 public void respond(String response) {
		 this.latestResponse = response;
	 }
}
