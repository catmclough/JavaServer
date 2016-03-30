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

	private Socket testClientSocket;
	private ClientWorker testClientWorker;
	private String simpleGet = "GET / HTTP/1.1\r\n";
	private String blankRequest = "";

	@Before
	public void setUp() throws Exception {
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
