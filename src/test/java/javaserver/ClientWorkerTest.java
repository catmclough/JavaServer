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
	private RequestBuilder requestBuilder;
	private Responder responder;

	@Before
	public void setUp() throws Exception {
		this.testClientSocket = new Socket();
		this.requestBuilder = new RequestBuilder();
		this.responder = new Responder();
		MockReader mockReader = new MockReader(stubGetRequestReader());
		MockSocketWriter mockWriter = new MockSocketWriter(mockOutputStream());
		this.testClientWorker = new ClientWorker(testClientSocket, mockReader, requestBuilder, responder, mockWriter);
	}

	@Test
	public void testRun() throws IOException {
	  testClientWorker.run();
	  assertEquals(responder.TWO_HUNDRED, testClientWorker.writer.latestResponse);
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

class MockReader extends Reader {
	MockReader(BufferedReader mockBufferedReader) {
		super(mockBufferedReader);
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
