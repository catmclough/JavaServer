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
	BufferedReader mockGetReader;

	@Before
	public void setUp() throws Exception {
		App.configureRoutes();
		Reader reader = new Reader(stubGetRequestReader());
		MockSocketWriter mockWriter = new MockSocketWriter(mockOutputStream());
		this.testClientWorker = new ClientWorker(reader, mockWriter);
	}

	@Test
	public void testRun() throws IOException {
	  testClientWorker.run();
	  String twoHundredResponse = HTTPStatusCode.TWO_HUNDRED.getStatusLine();
	  assertTrue(testClientWorker.writer.latestResponse.contains(twoHundredResponse));
	}


	private BufferedReader stubGetRequestReader() {
		String getRequest = "GET / HTTP/1.1\r\n";
		InputStream stubInputStreamWithGet = new ByteArrayInputStream(getRequest.getBytes());
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
