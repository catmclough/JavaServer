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
	MockClientWorker mockClientWorker;
	BufferedReader mockGetReader;
	private String getRequest = "GET / HTTP/1.1\r\n";

	@Before
	public void setUp() throws Exception {
		testClientSocket = new Socket();
		mockClientWorker = new MockClientWorker(testClientSocket);

		InputStream stubInputStreamWithGet = new ByteArrayInputStream(getRequest.getBytes());
		InputStreamReader inputReader = new InputStreamReader(stubInputStreamWithGet);
		mockGetReader = new BufferedReader(inputReader);
	}

	@Test
	public void testRun() throws IOException {
	  mockClientWorker.stubReaderWithRequest(mockGetReader);
	  mockClientWorker.setReader();
	  mockClientWorker.setWriter();
	  mockClientWorker.readAndRespond(mockClientWorker.reader, mockClientWorker.writer);
	  assertEquals(mockClientWorker.TWO_HUNDRED, mockClientWorker.writer.latestResponse);
  }

  @Test
  public void testRespond() throws IOException {
	  mockClientWorker.setWriter();
	  assertEquals(mockClientWorker.respond("GET", "/", mockClientWorker.writer), mockClientWorker.TWO_HUNDRED);
	  assertEquals(mockClientWorker.respond("GET", "/foobar", mockClientWorker.writer), mockClientWorker.FOUR_OH_FOUR);
  }

  class MockClientWorker extends ClientWorker {
    Socket client;
    Reader reader;
    BufferedReader bufferedReader;
    SocketWriter writer;

    MockClientWorker(Socket clientSocket) {
      super(clientSocket);
      this.client = clientSocket;
    }

    public void stubReaderWithRequest(BufferedReader bufferedReader) {
    	this.bufferedReader = bufferedReader;
    }

    @Override
    public void setReader() throws IOException {
    	this.reader = new Reader(bufferedReader);
    }

    public void setWriter() throws IOException {
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		this.writer = new MockSocketWriter(dataOutputStream);
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
}

